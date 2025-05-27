package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
}
