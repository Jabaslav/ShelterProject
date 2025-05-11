package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")  // Добавлено, хотя в DDL не было (рекомендуется для JPA)
    @EqualsAndHashCode.Include
    private Long id;

//    // Связь с чатом (Many-to-One)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "chat_id",
//            foreignKey = @ForeignKey(name = "chat_exists")  // Указываем имя constraint
//    )
//    @ToString.Exclude
//    private Chat chat;
//
    // Связь с автором (Many-to-One)
// /   @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "message_author_id",
//            foreignKey = @ForeignKey(name = "message_author_exists")
//    )
//    @ToString.Exclude
//    private User user;
    @Column(name="chat_id")
    private Long chatId;

    @Column(name="author_id")
    private Long authorId;



    @Column(name = "message_pic_addr", length = 50)
    private String imageAddress;

    @Column(name = "message_text", length = 140)
    private String text;

    @CreationTimestamp
    @Column(name = "message_creation_time", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime creationTime;
}
