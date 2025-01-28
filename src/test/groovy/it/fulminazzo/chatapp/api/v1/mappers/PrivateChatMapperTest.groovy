package it.fulminazzo.chatapp.api.v1.mappers

import spock.lang.Specification

class PrivateChatMapperTest extends Specification {

    def 'test that mapper correctly maps private chat to private chat dto'() {
        given:
        def firstUser = MapperTestUtils.firstUser()

        and:
        def secondUser = MapperTestUtils.secondUser()

        and:
        def privateChat = MapperTestUtils.privateChat(firstUser, secondUser)

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
        def firstUserDto = MapperTestUtils.firstUserDto()

        and:
        def secondUserDto = MapperTestUtils.secondUserDto()

        and:
        def privateChatDto = MapperTestUtils.privateChatDto(firstUserDto, secondUserDto)

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
