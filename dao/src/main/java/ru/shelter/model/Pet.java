package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pets")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "pet_name", nullable = false, length = 30)
    private String petName;

    @Column(name = "pet_pic_addr", unique = true, length = 50)
    private String petPicAddress;

    @Column(name = "pet_type", nullable = false, length = 30)
    private String petType;

    @Column(name = "pet_description", length = 140)
    private String petDescription;
}
