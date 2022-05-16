package ru.lofty.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lofty.library.model.Author;

/**
 * @author Alex Lavrentyev
 */

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
