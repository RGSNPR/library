package ru.lofty.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lofty.library.model.User;
import ru.lofty.library.repository.UserRepository;

/**
 * @author Alex Lavrentyev
 */

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(value = "/success")
    public String getSuccessPage() {
        return "success";
    }

}
