package ru.shelter.model;

//import jakarta.validation.constraints.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter                     // Генерирует геттеры, сеттеры, toString, equals, hashCode
@NoArgsConstructor          // Обязательный пустой конструктор для JPA
@AllArgsConstructor         // Опционально: конструктор со всеми полями (удобно для тестов)
@Builder                    // Добавляет builder-паттерн
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Post> orders = new ArrayList<>();


    @Column(name = "user_name", nullable = false, length = 30)
    private String userName;

    @Column(name = "user_birthday_date", nullable = false)
    private LocalDate userBirthdayDate;

    @Column(name = "user_email", unique = true, length = 30)
    private String userEmail;

    @Column(name = "user_phone_number", unique = true, length = 12)
    private String userPhoneNumber;

    @Column(name = "user_profile_pic_addr", length = 50)
    private String userProfilePicAddr;

    @Column(name = "salt", nullable = false, length = 15)
    private String salt;

    @Column(name = "user_password_hash", nullable = false, length = 128)
    private String userPasswordHash;
}

/*
@Data
public class User {

    private long id;

    @NotEmpty
    @NotBlank
    @Email
    private String email;

    @NotEmpty
    @NotBlank
    private String login;

    private String name;

    @PastOrPresent
    private LocalDate birthday;

    @NotNull
    private Set<Long> friends; // Инициализация коллекции

    public User() {
        friends = new HashSet<>();
    }

    public User(long id, @NotNull String email, @NotNull String login, String name, @NotNull LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        friends = new HashSet<>();
    }
}
*/
