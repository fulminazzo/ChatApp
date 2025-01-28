package it.fulminazzo.chatapp.api.v1.services

import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateChat
import it.fulminazzo.chatapp.api.v1.domain.entities.User
import it.fulminazzo.chatapp.api.v1.exceptions.HttpException
import it.fulminazzo.chatapp.api.v1.mappers.PrivateChatMapper
import it.fulminazzo.chatapp.api.v1.repositories.PrivateChatRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class PrivateChatServiceTest extends Specification {
    private static final User USER = User.builder().id(UUID.randomUUID()).username('fulminazzo').build()

    private PrivateChatRepository chatRepository
    private UserService userService
    private PrivateChatService chatService

    void setup() {
        chatRepository = Mock()
        userService = Mock()
        chatService = new PrivateChatService(chatRepository, userService)
    }

    def 'test find by user'() {
        given:
        UUID id = UUID.randomUUID()
        Pageable pageable = PageRequest.of(0, 2)

        and:
        def first = User.builder().id(id).build()

        and:
        def privateChats = [
                PrivateChat.builder().id(UUID.randomUUID())
                        .firstUser(first)
                        .secondUser(User.builder().id(UUID.randomUUID()).build())
                        .build(),
                PrivateChat.builder().id(UUID.randomUUID())
                        .firstUser(User.builder().id(UUID.randomUUID()).build())
                        .secondUser(first)
                        .build(),
        ]

        and:
        userService.findUserByIdOrThrow(id) >> first
        chatRepository.findAllByFirstUserOrSecondUser(_ as User, _ as User, _ as Pageable) >> new PageImpl<>(privateChats)

        and:
        def expected = privateChats.collect {
            PrivateChatMapper.INSTANCE.privateChatToPrivateChatDto(it)
        }

        when:
        def actual = chatService.findByUser(id, pageable)

        then:
        actual == expected
    }

    def 'test find one by user'() {
        given:
        def chat = PrivateChat.builder().id(UUID.randomUUID())
                .firstUser(first)
                .secondUser(second)
                .build()

        and:
        userService.findUserByIdOrThrow(USER.id) >> USER
        chatRepository.findById(chat.id) >> Optional.of(chat)

        when:
        def privateChatDto = chatService.findOneByUser(USER.id, chat.id)

        then:
        privateChatDto.id == chat.id

        where:
        first                                        | second
        USER                                         | User.builder().id(UUID.randomUUID()).build()
        User.builder().id(UUID.randomUUID()).build() | USER
    }

    def 'test create private chat with chat already existing should throw exception'() {
        given:
        User first = USER
        User second = User.builder().id(UUID.randomUUID()).username('felix').build()

        and:
        userService.findUserByIdOrThrow(first.id) >> first
        userService.findUserByUsernameOrThrow(second.username) >> second

        and:
        chatRepository.findByFirstUserAndSecondUser(first, second) >> chatOfFirst
        chatRepository.findByFirstUserAndSecondUser(second, first) >> chatOfSecond

        when:
        chatService.createChat(first.id, second.username)

        then:
        thrown(HttpException)

        where:
        chatOfFirst                    | chatOfSecond
        Optional.of(new PrivateChat()) | Optional.empty()
        Optional.empty()               | Optional.of(new PrivateChat())
    }

}
