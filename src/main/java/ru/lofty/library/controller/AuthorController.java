package ru.lofty.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
@RequestMapping("/authors")
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

        return viewPage(model, 1, "lastName", "asc");
    }

    @GetMapping("/page/{pageNumber}")
    @PreAuthorize("hasAuthority('authors:get')")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int pageNumber,
                           @Param("sortField") String sortField,
                           @Param("sortDir") String sortDir) {

        Page<Author> page = authorDao.readAll(pageNumber, sortField, sortDir);

        List<Author> authors = page.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("authors", authors);

        return "authors";
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('authors:get')")
    public String read(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("author", authorDao.read(id));

        return "author";
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
