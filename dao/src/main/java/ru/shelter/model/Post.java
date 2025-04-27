package ru.shelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_author_id")
    @JsonIgnore
    private User user;

    @Column(name = "post_pic_addr")
    private String imageAddress;

    @Column(name = "post_description")
    @Size(max = 140)
    private String description;

    @CreationTimestamp
    @Column(name = "post_creation_time")
    private LocalDateTime creationTime;
}