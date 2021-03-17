package com.movienight.app.controller.mvc;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RefreshScope
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
