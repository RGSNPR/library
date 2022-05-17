package ru.lofty.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.lofty.library.model.Author;
import ru.lofty.library.repository.AuthorRepository;

import java.util.Optional;

/**
 * @author Alex Lavrentyev
 */

@Service
public class AuthorDao {

    AuthorRepository authorRepository;

    @Autowired
    public AuthorDao(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author create(Author author) {
        return authorRepository.save(author);
    }

    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Optional<Author> read(Long id) {
        return authorRepository.findById(id);
    }

    public Optional<Author> update(Author author) {
        // можно сразу попробовать достать объект без проверки на наличие
        // и уже работать с persistence объектом обновляя его поля
        return Optional.ofNullable(author)
                .flatMap(p -> authorRepository.findById(p.getId())
                        .map(a -> {
                            a.setFirstName(p.getFirstName());
                            a.setLastName(p.getLastName());
                            return a;
                        })
                        .map(authorRepository::save)
                );
    }

    public boolean delete(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
