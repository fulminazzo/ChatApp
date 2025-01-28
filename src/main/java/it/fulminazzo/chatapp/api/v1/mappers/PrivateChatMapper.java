package it.fulminazzo.chatapp.api.v1.mappers;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateChatDto;
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PrivateChatMapper {

    PrivateChatMapper INSTANCE = Mappers.getMapper( PrivateChatMapper.class );

    @Mapping(source = "firstUser", target = "first")
    @Mapping(source = "secondUser", target = "second")
    PrivateChatDto privateChatToPrivateChatDto(PrivateChat privateChat);

    @Mapping(source = "first", target = "firstUser")
    @Mapping(source = "second", target = "secondUser")
    PrivateChat privateChatDtoToPrivateChat(PrivateChatDto privateChatDto);

}
