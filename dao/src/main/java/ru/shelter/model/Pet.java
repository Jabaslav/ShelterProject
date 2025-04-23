package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "pet_name", nullable = false, length = 30)
    private String name;

    @Column(name = "pet_pic_addr", unique = true, length = 50)
    private String imageAddress;

    @Column(name = "pet_type", nullable = false, length = 30)
    private String type;

    @Column(name = "pet_description", length = 140)
    private String description;
}
