package ru.lofty.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lofty.library.dao.AuthorDao;
import ru.lofty.library.model.Author;

import java.util.List;

/**
 * @author Alex Lavrentyev
 */

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {
    private final AuthorDao authorDao;

    @Autowired
    public AuthorController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Author author) {
        final boolean created = authorDao.create(author);

        return created ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    public List<Author> readAll() {
        return authorDao.readAll();
    }

    @GetMapping(value = "/{id}")
    public Author read(@PathVariable(name = "id") Long id) {
        return authorDao.read(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Author author) {
        final boolean updated =  authorDao.update(author, id);

        return updated ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = authorDao.delete(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
