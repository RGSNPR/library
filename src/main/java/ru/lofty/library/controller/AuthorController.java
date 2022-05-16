package ru.lofty.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lofty.library.dao.AuthorDao;
import ru.lofty.library.model.Author;

import java.util.List;

/**
 * @author Alex Lavrentyev
 */

@Controller
@RequestMapping(value = "/authors")
public class AuthorController {
    private final AuthorDao authorDao;

    @Autowired
    public AuthorController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('authors:post')")
    public ResponseEntity<?> create(@RequestBody Author author) {
        final boolean created = authorDao.create(author);

        return created ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('authors:get')")
    public String readAll(Model model) {
        model.addAttribute("authors", authorDao.readAll());
        return "authors";
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('authors:get')")
    public Author read(@PathVariable(name = "id") Long id) {
        return authorDao.read(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('authors:post')")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Author author) {
        final boolean updated =  authorDao.update(author, id);

        return updated ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('authors:post')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = authorDao.delete(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
