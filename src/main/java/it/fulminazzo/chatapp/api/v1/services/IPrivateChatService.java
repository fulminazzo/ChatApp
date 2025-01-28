package it.fulminazzo.chatapp.api.v1.services;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateChatDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IPrivateChatService {

    List<PrivateChatDto> findByUser(UUID userId, Pageable pageable);

    PrivateChatDto findOneByUser(UUID userId, UUID chatId);

    PrivateChatDto createChat(UUID userId, String otherUserUsername);

}
