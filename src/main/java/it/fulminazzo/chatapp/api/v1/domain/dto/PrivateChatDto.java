package it.fulminazzo.chatapp.api.v1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateChatDto {

    private UUID id;

    private UserDto first;

    private UserDto second;

}
