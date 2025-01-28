package it.fulminazzo.chatapp.api.v1.services;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateChatDto;
import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateMessageDto;
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateMessage;
import it.fulminazzo.chatapp.api.v1.mappers.PrivateChatMapper;
import it.fulminazzo.chatapp.api.v1.mappers.PrivateMessageMapper;
import it.fulminazzo.chatapp.api.v1.repositories.PrivateMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class PrivateMessageService implements IPrivateMessageService {

    private final PrivateMessageRepository messageRepository;

    private final IPrivateChatService privateChatService;

    @Override
    public List<PrivateMessageDto> findByUserAndChat(UUID userId, UUID chatId, Pageable pageable) {
        PrivateChatDto chatDto = privateChatService.findOneByUser(userId, chatId);
        List<PrivateMessage> messages = messageRepository.findAllByChat(
                PrivateChatMapper.INSTANCE.privateChatDtoToPrivateChat(chatDto),
                pageable
        );
        return messages.stream()
                .map(PrivateMessageMapper.INSTANCE::privateMessageToPrivateMessageDto)
                .toList();
    }

}
