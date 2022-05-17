package ru.lofty.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.Objects;
import java.util.Optional;

/**
 * @author Alex Lavrentyev
 */

// Лучше обернуть в AuthorDTO чтобы не отдавать с persistence объект.
@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorDao authorDao;

    @Autowired
    public AuthorController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('authors:post')")
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return Optional.ofNullable(author)
                .filter(f -> Objects.isNull(f.getId()))
                .map(authorDao::create)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                // Дополнительно можно сделать ControllerAdvice для обработки ошибок
//                .orElseThrow(() -> new IllegalArgumentException("Author might be not null and ID might be null"));
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * В параметрах запроса можно указывать page=&size=&sort=field,direction
     * field это имя поля
     * direction asc:desc - не обязательное, по умолчанию asc
     * @param pageable org.springframework.data.domain.Pageable
     * @return Page
     */
    @GetMapping
    @PreAuthorize("hasAuthority('authors:get')")
    public Page<Author> list(Pageable pageable) {
        // можно использовать для сравнения заготовленный массив, если видов сортировки несколько, как у книг.
        if (pageable.getSort().stream().allMatch(f -> Objects.equals("lastName", f.getProperty())) ||
                pageable.getSort().isEmpty() || pageable.isUnpaged()) {
            // можно так же проверять страницу, если хочется чтобы шло с 1, а не 0
            // при этом можно оборачивать результат в новый Page -> PageImpl<>
            return authorDao.findAll(pageable);
        } else {
            // Дополнительно можно сделать ControllerAdvice для обработки ошибок
            throw new RuntimeException();
        }
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('authors:get')")
    public ResponseEntity<Author> read(@PathVariable(name = "id") Long id, Model model) {
        return authorDao.read(id)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('authors:get')")
    public ResponseEntity<Author> update(@RequestBody Author author) {
        // нет большого смысла передавать id, если пробрасывать готовый объект
        return authorDao.update(author)
                .map(p -> new ResponseEntity<>(author, HttpStatus.OK))
                // Дополнительно можно сделать ControllerAdvice для обработки ошибок
//                .orElseThrow(IllegalArgumentException::new);
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('authors:post')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        // опять же лучше уже выкинуть свою ошибку, чтобы не гадать, "а что случилось?"
        final boolean deleted = authorDao.delete(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    // так же можно подумать насчет редактирование авторов у книги или тут, или в BookController
    // так как текущей реализации нет
}
