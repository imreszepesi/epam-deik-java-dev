package com.epam.training.ticketservice.ui.command;


import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;

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
    public String listMovies() {
        List<MovieDto> movies = movieService.listMovies();

        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            for (MovieDto movie : movies) {
                result.append(String.format("%s (%s, %d minutes)",
                        movie.getTitle(), movie.getGenre(), movie.getDuration()));
            }
            return result.toString();
        }
    }
    @ShellMethod(key = "update movie", value = "Update an existing movie")
    public String updateMovie(String title, String genre, Integer duration) {
        Optional<MovieDto> updatedMovie = movieService.updateMovie(title, genre, duration);
        return updatedMovie.map(movie -> "Update movie was successful: " + formatMovie(movie))
                .orElse("Update movie failed! Movie not found.");
    }

    @ShellMethod(key = "delete movie", value = "Delete an existing movie")
    public String deleteMovie(String title) {
        Optional<MovieDto> deletedMovie = movieService.deleteMovie(title);
        return deletedMovie.map(movie -> "Delete movie was successful: " + formatMovie(movie))
                .orElse("Delete movie failed! Movie not found.");
    }
    private String formatMovie(MovieDto movie) {
        return String.format("%s (%s, %d minutes)", movie.getTitle(), movie.getGenre(), movie.getDuration());
    }
}
