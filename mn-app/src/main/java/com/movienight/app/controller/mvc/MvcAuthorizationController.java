package com.movienight.app.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcAuthorizationController {

    @GetMapping
    public String home(){
        return "home";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }
}
