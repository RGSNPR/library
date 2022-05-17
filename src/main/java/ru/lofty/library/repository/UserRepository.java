package ru.lofty.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lofty.library.model.User;

import java.util.Optional;

/**
 * @author Alex Lavrentyev
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
