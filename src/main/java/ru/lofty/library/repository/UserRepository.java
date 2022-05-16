package ru.lofty.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lofty.library.model.User;

import java.util.Optional;

/**
 * @author Alex Lavrentyev
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
