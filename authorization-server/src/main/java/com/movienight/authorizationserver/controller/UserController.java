package com.movienight.authorizationserver.controller;

import com.movienight.authorizationserver.service.actor.ActorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController extends ActorService {

    @GetMapping("/user/me")
    public String user() {
        return getActorFromContext().getUsername();
    }

}
