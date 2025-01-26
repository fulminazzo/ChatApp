package it.fulminazzo.chatapp.backend.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "private_chats")
public class PrivateChat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "first_user_id")
    private User firstUser;

    @OneToOne(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "second_user_id")
    private User secondUser;

}
