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
import ru.lofty.library.dao.UserDao;
import ru.lofty.library.model.User;

import java.util.List;

/**
 * @author Alex Lavrentyev
 */

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('users:post')")
    public ResponseEntity<?> create(@RequestBody User user) {
        final boolean created = userDao.create(user);

        return created ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('users:get')")
    public String readAll(Model model) {

        return viewPage(model, 1, "username", "asc");
    }

    @GetMapping("/page/{pageNumber}")
    @PreAuthorize("hasAuthority('users:get')")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNumber") int pageNumber,
                           @Param("sortField") String sortField,
                           @Param("sortDir") String sortDir) {

        Page<User> page = userDao.readAll(pageNumber, sortField, sortDir);

        List<User> users = page.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('users:get')")
    public String read(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("user", userDao.read(id));

        return "users";
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('users:post')")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody User user) {
        final boolean updated =  userDao.update(user, id);

        return updated ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('users:post')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = userDao.delete(id);

        return deleted ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
