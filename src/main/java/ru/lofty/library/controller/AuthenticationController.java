package ru.lofty.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lofty.library.repository.UserRepository;

/**
 * @author Alex Lavrentyev
 */

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    // лучше использовать dao слой как минимум, но отдельный сервис для этого подойдет больше
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // это лишнее, т.к. ожидается что это rest приложение, и достаточно для этого грамотно составить конф.
    // безусловно можно /login кастомизировать, но без конфигурации и это работать не будет
    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    // это лишнее, т.к. ожидается что это rest приложение, и достаточно для этого грамотно составить конф.
    @GetMapping(value = "/success")
    public String getSuccessPage() {
        return "success";
    }

    // а вот регистрация не помешала бы

}
