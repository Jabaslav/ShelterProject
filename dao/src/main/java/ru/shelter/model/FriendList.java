package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.shelter.model.User;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "friend_list")
@IdClass(FriendListId.class)  // Для составного первичного ключа
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendList {

    // Класс для составного ID
    @EmbeddedId
    @EqualsAndHashCode.Include
    FriendListId friendListId;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "friend_id", nullable = false)
    private Long friendId;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    @CreationTimestamp
    @Column(name = "friendship_creation_time", updatable = false)
    private LocalDateTime lastStatusChangeTime;

    // Связь с пользователями (может быть ленивой для оптимизации)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", insertable = false, updatable = false)
    private User friend;

    // Enum для статусов дружбы
    public enum FriendshipStatus {
        PENDING, ACCEPTED, BLOCKED
    }
}
