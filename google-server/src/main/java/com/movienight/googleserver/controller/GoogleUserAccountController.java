package com.movienight.googleserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class GoogleUserAccountController {

    @GetMapping("/user/me")
    public String info(){
        return "Google User Account Authenticated";
    }

}
