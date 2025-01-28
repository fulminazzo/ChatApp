package it.fulminazzo.chatapp.api.v1.services;

import it.fulminazzo.chatapp.api.v1.repositories.PrivateMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class PrivateMessageService implements IPrivateMessageService {

    private final PrivateMessageRepository messageRepository;

}
