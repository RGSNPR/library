package ru.lofty.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lofty.library.dao.BookDao;
import ru.lofty.library.model.Book;

import java.util.List;

/**
 * @author Alex Lavrentyev
 */

@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookDao bookDao;

    @Autowired
    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book book) {
        final boolean created = bookDao.create(book);

        return created ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    public List<Book> readAll() {
        return bookDao.readAll();
    }

    @GetMapping(value = "/{id}")
    public Book read(@PathVariable(name = "id") Long id) {
        return bookDao.read(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Book book) {
        final boolean updated =  bookDao.update(book, id);

        return updated ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = bookDao.delete(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
