package it.fulminazzo.chatapp.api.v1.services

import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateChat
import it.fulminazzo.chatapp.api.v1.domain.entities.User
import it.fulminazzo.chatapp.api.v1.mappers.PrivateChatMapper
import it.fulminazzo.chatapp.api.v1.repositories.PrivateChatRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class PrivateChatServiceTest extends Specification {
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

}
