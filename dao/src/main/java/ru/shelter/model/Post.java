package ru.shelter.model;

import lombok.Data;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data // Генерирует геттеры, сеттеры, toString, equals, hashCode
@NoArgsConstructor // Обязательный пустой конструктор для JPA
@AllArgsConstructor // Опционально: конструктор со всеми полями
@Builder // Паттерн Builder для удобного создания объектов
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    // Связь Many-to-One с User (много постов → один автор)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "post_author_id",
            foreignKey = @ForeignKey(name = "post_author_exists") // Указываем имя constraint
    )
    @ToString.Exclude // Исключаем из toString, чтобы избежать рекурсии
    private User author; // Ссылка на сущность User, а не просто post_author_id

    @Column(name = "post_pic_addr", length = 50)
    private String pictureAddress;

    @Column(name = "post_description", length = 140)
    private String description;

    @Column(name = "post_creation_time", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime creationTime;
}

        /*
@Data
public class Post {

    Long id;

    long authorId;

    String description;

    Instant postDate;
}
         */
