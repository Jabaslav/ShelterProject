package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "chat_name", length = 50)
    private String name;

    @Column(name = "chat_pic_addr", length = 100)
    private String imageAddress;

    @CreationTimestamp
    @Column(name = "followship_creation_time", updatable = false)
    private LocalDateTime creationTime;

//    @OneToMany(mappedBy = "chat", cascade=CascadeType.ALL)
//    private List<Message> messages = new ArrayList<>();
//
//    public void addMessage(Message message)
//    {
//        messages.add(message);
//        message.setChat(this);
//    }

}
