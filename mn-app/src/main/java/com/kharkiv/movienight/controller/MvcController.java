package com.kharkiv.movienight.controller;

import com.kharkiv.movienight.transport.dto.movie.MovieFindDto;
import com.kharkiv.movienight.transport.dto.pageable.PageableDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Setter(onMethod_ = @Autowired)
public class MvcController {

    private MovieController movieController;

    @GetMapping
    public String home(){
        return "home";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("all-movies")
    public String allMovies(Model model){
        model.addAttribute("all_movies", movieController.findAll(new MovieFindDto(), new PageableDto()));
        return "movies";
    }

    @GetMapping("career")
    public String career() {
        return "career";
    }

    @GetMapping("about-me")
    public String aboutMe() {
        return "aboutMe";
    }

    @GetMapping("soon")
    public String soon() {
        return "soon";
    }

    @GetMapping("my-tickets")
    public String myTickets() {
        return "myTickets";
    }
}
