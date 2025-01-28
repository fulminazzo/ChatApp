package it.fulminazzo.chatapp.api.v1.controllers;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateChatDto;
import it.fulminazzo.chatapp.api.v1.services.IPrivateChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chats")
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

}
