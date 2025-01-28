package it.fulminazzo.chatapp.api.v1.services;

import it.fulminazzo.chatapp.api.v1.domain.entities.User;

import java.util.UUID;

public interface IUserService {

    User findUserByIdOrThrow(UUID id);

    User findUserByUsernameOrThrow(String username);

}
