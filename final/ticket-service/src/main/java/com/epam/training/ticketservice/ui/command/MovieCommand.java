package com.epam.training.ticketservice.ui.command;


import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {
    private final MovieService movieService;

    @ShellMethod(key = "create movie", value = "Movie Creation")
    public String createMovie(String title, String genre, Integer duration) {
        try {
            movieService.createMovie(title, genre,duration);
            return "Create movie was successful!";
        } catch (Exception e) {
            return "Create movie failed!";
        }
    }
    @ShellMethod(key = "list movies", value = "Get movies")
    public List<MovieDto> description(){
        return movieService.listMovies();

    }
}
