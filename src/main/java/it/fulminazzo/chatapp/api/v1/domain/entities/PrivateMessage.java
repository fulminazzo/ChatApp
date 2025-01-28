package it.fulminazzo.chatapp.api.v1.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "private_messages")
public class PrivateMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private PrivateChat chat;

    @OneToOne
    @JoinColumn(name = "first_user_id", nullable = false)
    private User from;

    @OneToOne
    @JoinColumn(name = "second_user_id", nullable = false)
    private User to;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String content;

}
