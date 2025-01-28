package it.fulminazzo.chatapp.api.v1.mappers;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateChatDto;
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateChat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PrivateChatMapper {

    PrivateChatMapper INSTANCE = Mappers.getMapper( PrivateChatMapper.class );

    PrivateChatDto privateChatToPrivateChatDto(PrivateChat privateChat);

    PrivateChat privateChatDtoToPrivateChat(PrivateChatDto privateChatDto);

}
