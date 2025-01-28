package it.fulminazzo.chatapp.api.v1.domain.dto

import it.fulminazzo.chatapp.api.v1.exceptions.HttpException
import org.springframework.http.HttpStatus
import spock.lang.Specification

class PrivateChatDtoTest extends Specification {

    def 'test that checkUser does not invert first and second with first id'() {
        given:
        def firstUserDto = new UserDto(UUID.randomUUID(), 'fulminazzo')

        and:
        def secondUserDto = new UserDto(UUID.randomUUID(), 'felix')

        and:
        def privateChatDto = new PrivateChatDto(UUID.randomUUID(), firstUserDto, secondUserDto)

        when:
        privateChatDto.checkUser(firstUserDto.id)

        then:
        privateChatDto.first == firstUserDto
        privateChatDto.second == secondUserDto
    }

    def 'test that checkUser inverts first and second with second id'() {
        given:
        def firstUserDto = new UserDto(UUID.randomUUID(), 'fulminazzo')

        and:
        def secondUserDto = new UserDto(UUID.randomUUID(), 'felix')

        and:
        def privateChatDto = new PrivateChatDto(UUID.randomUUID(), firstUserDto, secondUserDto)

        when:
        privateChatDto.checkUser(secondUserDto.id)

        then:
        privateChatDto.first == secondUserDto
        privateChatDto.second == firstUserDto
    }

    def 'test that checkUser throws exception with invalid id'() {
        given:
        def firstUserDto = new UserDto(UUID.randomUUID(), 'fulminazzo')

        and:
        def secondUserDto = new UserDto(UUID.randomUUID(), 'felix')

        and:
        def privateChatDto = new PrivateChatDto(UUID.randomUUID(), firstUserDto, secondUserDto)

        when:
        privateChatDto.checkUser(UUID.randomUUID())

        then:
        def e = thrown(HttpException)
        e.httpStatus == HttpStatus.FORBIDDEN
    }

}
