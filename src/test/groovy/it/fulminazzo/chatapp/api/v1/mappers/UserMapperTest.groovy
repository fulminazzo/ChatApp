package it.fulminazzo.chatapp.api.v1.mappers

import it.fulminazzo.chatapp.api.v1.domain.dto.UserDto
import it.fulminazzo.chatapp.api.v1.domain.entities.User
import spock.lang.Specification

class UserMapperTest extends Specification {

    def 'test that mapper correctly maps user to user dto'() {
        given:
        def user = User.builder()
                .id(UUID.randomUUID())
                .username('fulminazzo')
                .password('password')
                .build()

        when:
        def userDto = UserMapper.INSTANCE.userToUserDto(user)

        then:
        userDto.id == user.id
        userDto.username == user.username
    }

    def 'test that mapper correctly maps user dto to user'() {
        given:
        def userDto = new UserDto(UUID.randomUUID(), 'fulminazzo')

        when:
        def user = UserMapper.INSTANCE.userDtoToUser(userDto)

        then:
        user.id == userDto.id
        user.username == userDto.username
    }

}
