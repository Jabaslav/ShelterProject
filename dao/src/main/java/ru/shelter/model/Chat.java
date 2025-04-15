package ru.shelter.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "chat_name", length = 50)
    private String chatName;

    @Column(name = "chat_pic_addr", length = 50)
    private String chatPicAddress;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();
}
