package it.fulminazzo.chatapp.api.v1.repositories;

import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, UUID> {

}
