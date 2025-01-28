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

}
