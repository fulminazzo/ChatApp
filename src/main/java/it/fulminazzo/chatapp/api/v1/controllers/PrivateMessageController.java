package it.fulminazzo.chatapp.api.v1.controllers;

import it.fulminazzo.chatapp.api.v1.domain.dto.PrivateMessageDto;
import it.fulminazzo.chatapp.api.v1.domain.requests.GetPrivateMessageRequest;
import it.fulminazzo.chatapp.api.v1.services.IPrivateMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/private/messages")
@RequiredArgsConstructor
public class PrivateMessageController {

    private final IPrivateMessageService messageService;

    @GetMapping
    public List<PrivateMessageDto> getMessages(
            @RequestAttribute UUID userId,
            @RequestBody GetPrivateMessageRequest messageRequest,
            Pageable pageable
    ) {
        return messageService.findByUserAndChat(userId, messageRequest.chatId(), pageable);
    }

}
