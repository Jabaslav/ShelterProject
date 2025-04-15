package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "chat_participants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ChatParticipant.ChatParticipantId.class) // Для составного ключа
public class ChatParticipant {

    // Составной первичный ключ
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatParticipantId implements Serializable {
        private Long chat;
        private Long participant;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "chat_id",
            foreignKey = @ForeignKey(name = "chat_exists")
    )
    @ToString.Exclude
    private Chat chat;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "participant_id",
            foreignKey = @ForeignKey(name = "user_exists")
    )
    @ToString.Exclude
    private User participant;

    @Column(name = "participant_role", columnDefinition = "VARCHAR(30) DEFAULT 'default'")
    @Enumerated(EnumType.STRING)
    private ParticipantRole role = ParticipantRole.DEFAULT;

    @Column(name = "is_participant_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

    public enum ParticipantRole {
        ADMIN, MODERATOR, DEFAULT
    }
}
