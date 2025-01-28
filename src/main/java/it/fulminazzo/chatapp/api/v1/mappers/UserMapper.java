package it.fulminazzo.chatapp.api.v1.mappers;

import it.fulminazzo.chatapp.api.v1.domain.dto.UserDto;
import it.fulminazzo.chatapp.api.v1.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto dto);

}
