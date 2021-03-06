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
import ru.lofty.library.dao.BookDao;
import ru.lofty.library.model.Book;

import java.util.List;

/**
 * @author Alex Lavrentyev
 */

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDao bookDao;

    @Autowired
    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('books:post')")
    public ResponseEntity<?> create(@RequestBody Book book) {
        final boolean created = bookDao.create(book);

        return created ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('books:get')")
    public String readAll(Model model) {

        return viewPage(model, 1, "name", "asc");
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('books:get')")
    public Book read(@PathVariable(name = "id") Long id) {
        return bookDao.read(id);
    }

    @GetMapping("/page/{pageNumber}")
    @PreAuthorize("hasAuthority('books:get')")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int pageNumber,
                           @Param("sortField") String sortField,
                           @Param("sortDir") String sortDir) {

        Page<Book> page = bookDao.readAll(pageNumber, sortField, sortDir);

        List<Book> books = page.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("books", books);

        return "books";
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('books:post')")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Book book) {
        final boolean updated =  bookDao.update(book, id);

        return updated ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('books:post')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = bookDao.delete(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
