package it.fulminazzo.chatapp.api.v1.mappers

import spock.lang.Specification

class PrivateMessageMapperTest extends Specification {

    def 'test that mapper correctly maps private message to private message dto'() {
        given:
        def from = MapperTestUtils.firstUser()

        and:
        def to = MapperTestUtils.secondUser()

        and:
        def privateMessage = MapperTestUtils.privateMessage(
                MapperTestUtils.privateChat(from, to),
                from, to
        )

        when:
        def privateMessageDto = PrivateMessageMapper.INSTANCE.privateMessageToPrivateMessageDto(privateMessage)

        then:
        privateMessageDto.from.id == from.id
        privateMessageDto.from.username == from.username
        privateMessageDto.to.id == to.id
        privateMessageDto.to.username == to.username
        privateMessageDto.timestamp == privateMessage.timestamp
        privateMessageDto.content == privateMessage.content
    }

    def 'test that mapper correctly maps private message dto to private message'() {
        given:
        def fromDto = MapperTestUtils.firstUserDto()

        and:
        def toDto = MapperTestUtils.secondUserDto()

        and:
        def privateMessage = MapperTestUtils.privateMessageDto(
                fromDto, toDto
        )

        when:
        def privateMessageDto = PrivateMessageMapper.INSTANCE.privateMessageDtoToPrivateMessage(privateMessage)

        then:
        privateMessageDto.from.id == fromDto.id
        privateMessageDto.from.username == fromDto.username
        privateMessageDto.to.id == toDto.id
        privateMessageDto.to.username == toDto.username
        privateMessageDto.timestamp == privateMessage.timestamp
        privateMessageDto.content == privateMessage.content
    }

}
