package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.shelter.model.User;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "friend_list")
@IdClass(FriendListId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendList {

    @Id
    private Long userId; // Убраны явные @Column, так как они определены в FriendListId

    @Id
    private Long friendId;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime lastStatusChangeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", insertable = false, updatable = false)
    private User friend;

    public enum FriendshipStatus {
        PENDING, ACCEPTED, BLOCKED
    }
}