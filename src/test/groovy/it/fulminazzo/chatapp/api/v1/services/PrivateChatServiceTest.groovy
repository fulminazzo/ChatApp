package it.fulminazzo.chatapp.api.v1.services

import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateChat
import it.fulminazzo.chatapp.api.v1.domain.entities.User
import it.fulminazzo.chatapp.api.v1.exceptions.HttpException
import it.fulminazzo.chatapp.api.v1.mappers.PrivateChatMapper
import it.fulminazzo.chatapp.api.v1.repositories.PrivateChatRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
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
        Pageable pageable = PageRequest.of(0, 2)

        and:
        def user = generateUser()

        and:
        def privateChats = [
                generateChat(user, generateUser()),
                generateChat(generateUser(), user),
        ]

        and:
        userService.findUserByIdOrThrow(user.id) >> user
        chatRepository.findAllByFirstUserOrSecondUser(_ as User, _ as User, _ as Pageable) >> new PageImpl<>(privateChats)

        and:
        def expected = privateChats.collect {
            PrivateChatMapper.INSTANCE.privateChatToPrivateChatDto(it)
        }

        when:
        def actual = chatService.findByUser(user.id, pageable)

        then:
        actual == expected
    }

    def 'test find one by user'() {
        given:
        def chat = generateChat(first, second)

        and:
        userService.findUserByIdOrThrow(USER.id) >> USER
        chatRepository.findById(chat.id) >> Optional.of(chat)

        when:
        def privateChatDto = chatService.findOneByUser(USER.id, chat.id)

        then:
        privateChatDto.id == chat.id

        where:
        first          | second
        USER           | generateUser()
        generateUser() | USER
    }

    def 'test find one by user of third user should throw exception'() {
        given:
        def chat = generateChat(generateUser(), generateUser())

        and:
        userService.findUserByIdOrThrow(USER.id) >> USER
        chatRepository.findById(chat.id) >> Optional.of(chat)

        when:
        chatService.findOneByUser(USER.id, chat.id)

        then:
        def e = thrown(HttpException)
        e.httpStatus == HttpStatus.FORBIDDEN
    }

    def 'test create private chat with chat already existing should throw exception'() {
        given:
        User first = USER
        User second = generateUser()

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

    private static PrivateChat generateChat(User first, User second) {
        return PrivateChat.builder().id(UUID.randomUUID())
                .firstUser(first)
                .secondUser(second)
                .build()
    }

    private static User generateUser() {
        return User.builder().id(UUID.randomUUID()).username('felix').password('password').build()
    }

}
