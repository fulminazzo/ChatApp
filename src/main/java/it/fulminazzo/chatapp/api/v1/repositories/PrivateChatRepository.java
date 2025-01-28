package it.fulminazzo.chatapp.api.v1.repositories;

import it.fulminazzo.chatapp.api.v1.domain.entities.PrivateChat;
import it.fulminazzo.chatapp.api.v1.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, UUID> {

    Page<PrivateChat> findAllByFirstUserOrSecondUser(User firstUser, User secondUser, Pageable pageable);

    Optional<PrivateChat> findByFirstUserAndSecondUser(User firstUser, User secondUser);

}
