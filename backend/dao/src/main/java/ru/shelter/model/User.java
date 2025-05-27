package ru.shelter.model;

//import jakarta.validation.constraints.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor          // Обязательный пустой конструктор для JPA
@AllArgsConstructor         // Опционально: конструктор со всеми полями (удобно для тестов)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private Long id;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Post> posts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL)
//    private List<Message> messages = new ArrayList<>();

    @Column(name = "user_name", nullable = false, length = 30)
    private String name;

    @Column(name = "user_birthday_date", nullable = false)
    private LocalDate birthdayDate;

    @Column(name = "user_email", unique = true, length = 100)
    private String email;

    @Column(name = "user_phone_number", unique = true, length = 20)
    private String phoneNumber;

    @Column(name = "user_profile_pic_addr", length = 100)
    private String profilePicAddress;

    @Column(name = "user_password_hash", nullable = false, length = 60)
    private String password;

    @CreationTimestamp
    @Column(name="registered_since", updatable = false)
    private LocalDateTime registerTime;

    @Enumerated(EnumType.STRING)
    @Column(name="role", updatable = false)
    private User.Role role=Role.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    public enum Role{
        USER,
        ADMIN
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    public void addPost(Post post)
//    {
//        posts.add(post);
//        post.setUser(this);
//    }
//
//    public void addMessage(Message message)
//    {
//        messages.add(message);
//        message.setUser(this);
//    }
}


