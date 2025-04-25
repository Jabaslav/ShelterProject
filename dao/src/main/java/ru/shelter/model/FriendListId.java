package ru.shelter.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendListId implements Serializable {
    private Long userId;
    private Long friendId; // Исправлено на friendId (строчная буква)
}