package it.fulminazzo.chatapp.api.v1.controllers;

import it.fulminazzo.chatapp.api.v1.services.IPrivateMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/messages")
@RequiredArgsConstructor
public class PrivateMessageController {

    private final IPrivateMessageService messageService;

}
