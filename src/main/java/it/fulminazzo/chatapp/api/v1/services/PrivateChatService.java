package it.fulminazzo.chatapp.api.v1.services;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateChatDto;
import it.fulminazzo.chatapp.api.v1.domain.entities.User;
import it.fulminazzo.chatapp.api.v1.mappers.PrivateChatMapper;
import it.fulminazzo.chatapp.api.v1.repositories.PrivateChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

}
