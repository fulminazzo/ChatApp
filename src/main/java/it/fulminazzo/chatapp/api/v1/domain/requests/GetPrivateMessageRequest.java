package it.fulminazzo.chatapp.api.v1.domain.requests;

import java.util.UUID;

public record GetPrivateMessageRequest(UUID chatId) {

}
