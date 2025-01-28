package it.fulminazzo.chatapp.api.v1.mappers

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateChatDto
import it.fulminazzo.chatapp.api.v1.domain.dto.UserDto
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateChat
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateMessage
import it.fulminazzo.chatapp.api.v1.domain.entities.User
import spock.lang.Specification

import java.time.LocalDateTime

class PrivateChatMapperTest extends Specification {

    def 'test that mapper correctly maps private chat to private chat dto'() {
        given:
        def firstUser = User.builder()
                .id(UUID.randomUUID())
                .username('fulminazzo')
                .password('password')
                .build()

        and:
        def secondUser = User.builder()
                .id(UUID.randomUUID())
                .username('felix')
                .password('password')
                .build()

        and:
        def privateChat = PrivateChat.builder()
                .id(UUID.randomUUID())
                .firstUser(firstUser)
                .secondUser(secondUser)
                .messages(Arrays.asList(PrivateMessage.builder()
                        .id(UUID.randomUUID())
                        .from(firstUser)
                        .to(secondUser)
                        .timestamp(LocalDateTime.now())
                        .content('Hello, World!')
                        .build()
                ))
                .build()

        when:
        def privateChatDto = PrivateChatMapper.INSTANCE.privateChatToPrivateChatDto(privateChat)

        then:
        privateChatDto.id == privateChat.id
        privateChatDto.first.id == firstUser.id
        privateChatDto.first.username == firstUser.username
        privateChatDto.second.id == secondUser.id
        privateChatDto.second.username == secondUser.username
    }

    def 'test that mapper correctly maps private chat dto to private chat'() {
        given:
        def firstUserDto = new UserDto(UUID.randomUUID(), 'fulminazzo')

        and:
        def secondUserDto = new UserDto(UUID.randomUUID(), 'felix')

        and:
        def privateChatDto = new PrivateChatDto(UUID.randomUUID(), firstUserDto, secondUserDto)

        when:
        def privateChat = PrivateChatMapper.INSTANCE.privateChatDtoToPrivateChat(privateChatDto)

        then:
        privateChat.id == privateChatDto.id
        privateChat.firstUser.id == firstUserDto.id
        privateChat.firstUser.username == firstUserDto.username
        privateChat.secondUser.id == secondUserDto.id
        privateChat.secondUser.username == secondUserDto.username
    }

}
