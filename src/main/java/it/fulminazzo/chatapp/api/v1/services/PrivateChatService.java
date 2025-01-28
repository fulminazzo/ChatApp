package it.fulminazzo.chatapp.api.v1.services;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateChatDto;
import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateChat;
import it.fulminazzo.chatapp.api.v1.domain.entities.User;
import it.fulminazzo.chatapp.api.v1.exceptions.HttpException;
import it.fulminazzo.chatapp.api.v1.mappers.PrivateChatMapper;
import it.fulminazzo.chatapp.api.v1.repositories.PrivateChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class PrivateChatService implements IPrivateChatService {

    private final PrivateChatRepository chatRepository;

    private final UserService userService;

    @Override
    public List<PrivateChatDto> findByUser(UUID userId, Pageable pageable) {
        User user = userService.findUserByIdOrThrow(userId);
        return chatRepository.findAllByFirstUserOrSecondUser(user, user, pageable).stream()
                .map(PrivateChatMapper.INSTANCE::privateChatToPrivateChatDto)
                .toList();
    }

    @Override
    public PrivateChatDto findOneByUser(UUID userId, UUID chatId) {
        User user = userService.findUserByIdOrThrow(userId);
        PrivateChat privateChat = findChatByIdOrThrow(chatId);
        if (privateChat.getFirstUser().equals(user) || privateChat.getSecondUser().equals(user))
            return PrivateChatMapper.INSTANCE.privateChatToPrivateChatDto(privateChat);
        else throw new HttpException(HttpStatus.FORBIDDEN);
    }

    public PrivateChat findChatByIdOrThrow(UUID chatId) {
        return chatRepository.findById(chatId).orElseThrow(() ->
                new HttpException(HttpStatus.NOT_FOUND, "Could not find chat with id " + chatId));
    }

    @Override
    public PrivateChatDto createChat(UUID userId, String otherUserUsername) {
        User user = userService.findUserByIdOrThrow(userId);
        User otherUser = userService.findUserByUsernameOrThrow(otherUserUsername);
        if (chatRepository.findByFirstUserAndSecondUser(user, otherUser).isPresent() ||
                chatRepository.findByFirstUserAndSecondUser(otherUser, user).isPresent())
            throw new HttpException(HttpStatus.CONFLICT, String.format("Chat with user '%s' already exists", otherUserUsername));
        return PrivateChatMapper.INSTANCE.privateChatToPrivateChatDto(chatRepository.save(PrivateChat.builder()
                .firstUser(user)
                .secondUser(otherUser)
                .build()));
    }

}
