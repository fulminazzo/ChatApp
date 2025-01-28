package it.fulminazzo.chatapp.api.v1.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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

    @OneToOne
    @JoinColumn(name = "first_user_id", nullable = false)
    private User firstUser;

    @OneToOne
    @JoinColumn(name = "second_user_id", nullable = false)
    private User secondUser;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private List<PrivateMessage> messages = new ArrayList<>();

}
