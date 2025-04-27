package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

}
