package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.Post;

@Repository
public interface PostDao extends JpaRepository<Post, Long> {
}
