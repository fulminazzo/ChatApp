package it.fulminazzo.chatapp.api.v1.mappers

import it.fulminazzo.chatapp.api.v1.domain.dto.UserDto
import it.fulminazzo.chatapp.api.v1.domain.entities.User

final class MapperTestUtils {

    static User firstUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .username('fulminazzo')
                .password('password')
                .build()
    }

    static UserDto firstUserDto() {
        return new UserDto(UUID.randomUUID(), 'fulminazzo')
    }

}
