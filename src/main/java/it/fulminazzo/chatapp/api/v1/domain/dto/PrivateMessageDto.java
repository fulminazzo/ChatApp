package it.fulminazzo.chatapp.api.v1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessageDto {

    private UserDto from;

    private UserDto to;

    private LocalDateTime timestamp;

    private String content;

}
