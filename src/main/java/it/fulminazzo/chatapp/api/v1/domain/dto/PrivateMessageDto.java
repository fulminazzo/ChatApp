package it.fulminazzo.chatapp.api.v1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessageDto {

    private UUID chatId;

    private UserDto from;

    private UserDto to;

    private LocalDateTime timestamp;

    private String content;

}
