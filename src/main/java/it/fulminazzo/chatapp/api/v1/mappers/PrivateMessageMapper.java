package it.fulminazzo.chatapp.api.v1.mappers;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateMessageDto;
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PrivateMessageMapper {

    PrivateMessageMapper INSTANCE = Mappers.getMapper( PrivateMessageMapper.class );

    PrivateMessageDto privateMessageToPrivateMessageDto(PrivateMessage privateMessage);

    PrivateMessage privateMessageDtoToPrivateMessage(PrivateMessageDto privateMessageDto);

}
