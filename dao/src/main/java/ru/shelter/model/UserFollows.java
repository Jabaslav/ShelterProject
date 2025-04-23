package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_follows")
@IdClass(UserFollowsId.class)  // Для составного первичного ключа
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollows {

    // Класс для составного ID
    @EmbeddedId
    @EqualsAndHashCode.Include
    UserFollowsId userFollowsId;

    @Id
    @Column(name = "follower_id", nullable = false)
    private Long followerId;

    @Id
    @Column(name = "following_id", nullable = false)
    private Long followingId;

    @CreationTimestamp
    @Column(name = "followship_creation_time", updatable = false)
    private LocalDateTime followshipCreationTime;

    // Связи с пользователями
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", insertable = false, updatable = false)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", insertable = false, updatable = false)
    private User following;
}
