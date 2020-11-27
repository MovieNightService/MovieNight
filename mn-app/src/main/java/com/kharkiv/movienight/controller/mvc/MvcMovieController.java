package com.kharkiv.movienight.controller.mvc;

import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.service.movie.MovieService;
import com.kharkiv.movienight.transport.dto.movie.MovieCreateDto;
import com.kharkiv.movienight.transport.dto.movie.MovieFindDto;
import com.kharkiv.movienight.transport.dto.movie.MovieUpdateDto;
import com.kharkiv.movienight.transport.dto.pageable.PageableDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@Setter(onMethod_ = @Autowired)
public class MvcMovieController {

    private MovieService movieService;

    @GetMapping("all-movies")
    public String allMoviesGetTemplate(Model model) {
        model.addAttribute("all_movies", movieService.findAll(new MovieFindDto(), new PageableDto()));
        model.addAttribute("error", "");
        return "movies";
    }

    @GetMapping("single-movie")
    public String singleMovieGetTemplate(Model model, @RequestParam Long id) {
        Movie singleMovie = movieService.findById(id);
        model.addAttribute("singleMovie", singleMovie);
        model.addAttribute("error", "");


        byte[] encodeBase64 = Base64.getEncoder().encode(singleMovie.getImage());
        model.addAttribute("image", new String(encodeBase64, StandardCharsets.UTF_8));


        return "singleMovie";
    }

    @GetMapping("update-movie")
    public String updateMovieGetTemplate(@RequestParam Long id, Model model) {
        model.addAttribute("movie", movieService.findById(id));
        model.addAttribute("error", "");
        return "updateMovie";
    }

    @GetMapping("create-movie")
    public String createMovieGetTemplate(Model model) {
        model.addAttribute("movie", new MovieCreateDto());
        model.addAttribute("error", "");
        return "createMovie";
    }

    @PostMapping("update-movie")
    public String updateMovie(@RequestParam Long id, @ModelAttribute MovieUpdateDto movie, Model model) {
        try {
            movieService.update(id, movie);
        } catch (Throwable ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/update-movie?id=" + id + "&error";
        }
        return "redirect:/single-movie?id=" + id + "&success";
    }

    @PostMapping("create-movie")
    public String createMovie(@ModelAttribute MovieCreateDto movie, Model model) {
        try {
            movieService.create(movie);
        } catch (Throwable ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/create-movie#error";
        }
        return "redirect:/all-movies#success";
    }

    @GetMapping("delete-movie")
    public String deleteMovie(@RequestParam Long id, Model model) {
        try {
            movieService.delete(id);
        } catch (Throwable ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/single-movie?id=" + id + "&error";
        }
        return "redirect:/all-movies#success";
    }
}
