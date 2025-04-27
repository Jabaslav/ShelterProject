package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "published_pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PublishedPetId.class)  // Для составного ключа
public class PublishedPet {

    // Составной первичный ключ
    @Id
    @Column(name="pet_id")
    private Long petId;

    @Id
    @Column(name="post_id")
    private Long postId;

    @MapsId("petId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "pet_id",
            foreignKey = @ForeignKey(name = "fk_published_pet"),
            nullable = false
    )
    @ToString.Exclude
    private Pet pet;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "publication_id",
            foreignKey = @ForeignKey(name = "fk_published_post"),
            nullable = false
    )
    @ToString.Exclude
    private Post post;

    // Дополнительные поля (если нужны)
    @CreationTimestamp
    @Column(name = "publication_date")
    private LocalDateTime petAddedDate;
}

