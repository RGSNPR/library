package ru.lofty.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lofty.library.model.Book;

/**
 * @author Alex Lavrentyev
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
