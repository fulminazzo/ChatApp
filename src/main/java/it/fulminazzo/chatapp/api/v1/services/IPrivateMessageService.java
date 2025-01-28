package it.fulminazzo.chatapp.api.v1.services;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateMessageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IPrivateMessageService {

    List<PrivateMessageDto> findByUserAndChat(UUID userId, UUID chatId, Pageable pageable);

}
