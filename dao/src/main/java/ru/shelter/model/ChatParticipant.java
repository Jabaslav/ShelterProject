package ru.shelter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "chat_participants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ChatParticipantId.class) // Для составного ключа
public class ChatParticipant {

    // Составной первичный ключ
    @Id
    @Column(name="chat_id")
    private Long chatId;

    @Id
    @Column(name="participant_id")
    private Long userId;

    @MapsId("chatId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "chat_id",
            foreignKey = @ForeignKey(name = "chat_exists")
    )
    @ToString.Exclude
    private Chat chat;


    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "participant_id",
            foreignKey = @ForeignKey(name = "user_exists")
    )
    @ToString.Exclude
    private User user;

    @Column(name = "participant_role", columnDefinition = "VARCHAR(30) DEFAULT 'default'")
    @Enumerated(EnumType.STRING)
    private ParticipantRole role = ParticipantRole.basic;

    @Column(name = "is_participant_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

    public enum ParticipantRole {
        admin, moderator, basic
    }

}

