package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "pet_owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PetOwnerId.class)  // Для составного ключа
public class PetOwner {

    // Составной первичный ключ
    @EmbeddedId
    @EqualsAndHashCode.Include
    PetOwnerId petOwnerId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_pet_owner_user"),
            nullable = false
    )
    @ToString.Exclude
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "pet_id",
            foreignKey = @ForeignKey(name = "fk_pet_owner_pet"),
            nullable = false
    )
    @ToString.Exclude
    private Pet pet;

    @CreationTimestamp
    @Column(name = "ownership_since")
    private LocalDateTime ownershipCreationTime;
}

