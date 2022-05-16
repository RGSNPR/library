package ru.lofty.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lofty.library.model.Author;
import ru.lofty.library.repository.AuthorRepository;

import java.util.List;

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

    public boolean create(Author author) {
        authorRepository.save(author);

        return true;
    }

    public List<Author> readAll() {
        return authorRepository.findAll();
    }

    public Author read(Long id) {
        return authorRepository.getById(id);
    }

    public boolean update(Author author, Long id) {
        if (authorRepository.existsById(id)) {
            author.setId(id);
            authorRepository.save(author);
            return true;
        }

        return false;
    }

    public boolean delete(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
