package it.fulminazzo.chatapp.api.v1.mappers

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateChatDto
import it.fulminazzo.chatapp.api.v1.domain.dto.UserDto
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateChat
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateMessage
import it.fulminazzo.chatapp.api.v1.domain.entities.User

import java.time.LocalDateTime

final class MapperTestUtils {

    static User firstUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .username('fulminazzo')
                .password('password')
                .build()
    }

    static User secondUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .username('felix')
                .password('password')
                .build()
    }

    static PrivateChat privateChat(User firstUser, User secondUser) {
        return PrivateChat.builder()
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
    }

    static PrivateMessage privateMessage(PrivateChat privateChat, User from, User to) {
        return PrivateMessage.builder()
                .id(UUID.randomUUID())
                .chat(privateChat)
                .from(from)
                .to(to)
                .timestamp(LocalDateTime.now())
                .content('Hello, World!')
                .build()
    }

    static UserDto firstUserDto() {
        return new UserDto(UUID.randomUUID(), 'fulminazzo')
    }

    static UserDto secondUserDto() {
        return new UserDto(UUID.randomUUID(), 'felix')
    }

    static PrivateChatDto privateChatDto(UserDto firstUserDto, UserDto secondUserDto) {
        return new PrivateChatDto(UUID.randomUUID(), firstUserDto, secondUserDto)
    }

}
