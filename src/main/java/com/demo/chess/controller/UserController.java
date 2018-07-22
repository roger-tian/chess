package com.demo.chess.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chess/user")
public class UserController {
    @RequestMapping("/login")
    String UserLogin(String user, String password) {

        return "SUCCESS";
    }

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
}
