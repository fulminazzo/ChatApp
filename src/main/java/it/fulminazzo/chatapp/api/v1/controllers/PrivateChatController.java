package it.fulminazzo.chatapp.api.v1.controllers;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateChatDto;
import it.fulminazzo.chatapp.api.v1.domain.requests.PrivateChatRequest;
import it.fulminazzo.chatapp.api.v1.services.IPrivateChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/private/chats")
@RequiredArgsConstructor
public class PrivateChatController {

    private final IPrivateChatService privateChatService;

    @GetMapping
    public List<PrivateChatDto> getChats(
            @RequestAttribute UUID userId,
            Pageable pageable
    ) {
        return privateChatService.findByUser(userId, pageable);
    }

    @PostMapping
    public PrivateChatDto createChat(
            @RequestAttribute UUID userId,
            @RequestBody PrivateChatRequest chatRequest
    ) {
        return privateChatService.createChat(userId, chatRequest.otherUserUsername());
    }

}
