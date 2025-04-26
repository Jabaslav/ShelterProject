package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "pet_owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetOwner {
    @EmbeddedId
    private PetOwnerId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("petId")
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @CreationTimestamp
    @Column(name = "ownership_since")
    private LocalDateTime ownershipCreationTime;
}