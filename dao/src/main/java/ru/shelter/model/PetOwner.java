package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "pet_owners")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PetOwner.PetOwnerId.class)  // Для составного ключа
public class PetOwner {

    // Составной первичный ключ
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PetOwnerId implements Serializable {
        private Long user;
        private Long pet;
    }

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

    // Дополнительные поля (если нужны)
    // @Column(name = "ownership_since")
    // private LocalDate ownershipSince;
}
