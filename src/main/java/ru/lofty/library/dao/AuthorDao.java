package ru.lofty.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.lofty.library.model.Author;
import ru.lofty.library.repository.AuthorRepository;

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

    public Page<Author> readAll(int pageNumber, String sortField, String sortDir) {

        int pageSize = 3;

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );

        return authorRepository.findAll(pageable);
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
