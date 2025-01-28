package it.fulminazzo.chatapp.api.v1.services;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateMessageDto;
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateChat;
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateMessage;
import it.fulminazzo.chatapp.api.v1.domain.entities.User;
import it.fulminazzo.chatapp.api.v1.exceptions.HttpException;
import it.fulminazzo.chatapp.api.v1.mappers.PrivateMessageMapper;
import it.fulminazzo.chatapp.api.v1.repositories.PrivateMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class PrivateMessageService implements IPrivateMessageService {

    private final PrivateMessageRepository messageRepository;

    private final IPrivateChatService privateChatService;

    @Override
    public List<PrivateMessageDto> findByUserAndChat(UUID userId, UUID chatId, Pageable pageable) {
        PrivateChat chat = privateChatService.findOneByUser(userId, chatId);
        List<PrivateMessage> messages = messageRepository.findAllByChat(chat, pageable);
        return messages.stream()
                .map(PrivateMessageMapper.INSTANCE::privateMessageToPrivateMessageDto)
                .toList();
    }

    @Override
    public PrivateMessageDto addMessage(UUID userId, PrivateMessageDto messageDto) {
        PrivateChat chat = privateChatService.findOneByUser(userId, messageDto.getChatId());
        if (!chat.getFirstUser().getId().equals(userId) && !chat.getSecondUser().getId().equals(userId))
            throw new HttpException(HttpStatus.FORBIDDEN);
        User from = chat.getFirstUser();
        User to = chat.getSecondUser();
        if (!from.getId().equals(userId)) {
            User tmp = from;
            from = to;
            to = tmp;
        }
        PrivateMessage message = PrivateMessage.builder()
                .chat(chat)
                .from(from)
                .to(to)
                .timestamp(LocalDateTime.now())
                .content(messageDto.getContent())
                .build();
        return PrivateMessageMapper.INSTANCE.privateMessageToPrivateMessageDto(messageRepository.save(message));
    }

}
