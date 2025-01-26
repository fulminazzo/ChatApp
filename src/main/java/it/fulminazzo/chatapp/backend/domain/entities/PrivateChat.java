package it.fulminazzo.chatapp.backend.domain.entities;

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

    @OneToOne(mappedBy = "id")
    @JoinColumn(name = "first_user_id")
    private User firstUser;

    @OneToOne(mappedBy = "id")
    @JoinColumn(name = "second_user_id")
    private User secondUser;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<PrivateMessage> messages = new ArrayList<>();

}
