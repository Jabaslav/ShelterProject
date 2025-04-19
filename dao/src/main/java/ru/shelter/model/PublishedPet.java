package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;
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
    @EmbeddedId
    PublishedPetId publishedPetId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "pet_id",
            foreignKey = @ForeignKey(name = "fk_published_pet"),
            nullable = false
    )
    @ToString.Exclude
    private Pet pet;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "publication_id",  // Обратите внимание: в DDL используется publication_id, а не post_id
            foreignKey = @ForeignKey(name = "fk_published_post"),
            nullable = false
    )
    @ToString.Exclude
    private Post post;

    // Дополнительные поля (если нужны)
     @Column(name = "publication_date")
     private LocalDateTime petAddedDate;
}

