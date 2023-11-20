package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    @Override
    public void createMovie(String title, String genre, Integer duration) {
        Movie movie = new Movie(title, genre, duration);
        movieRepository.save(movie);
    }

    @Override
    public Optional<MovieDto> updateMovie(String title, String genre, Integer duration) {
        Optional<Movie> optionalMovie = movieRepository.findByTitle(title);

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movie.setGenre(genre);
            movie.setDuration(duration);
            movieRepository.save(movie);

            MovieDto updatedMovieDto = new MovieDto(movie.getTitle(), movie.getGenre(), movie.getDuration());
            return Optional.of(updatedMovieDto);
        } else {
            return Optional.empty();
        }
    }


    @Override
    public Optional<MovieDto> deleteMovie(String title) {
        Optional<Movie> optionalMovie = movieRepository.findByTitle(title);

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movieRepository.delete(movie);

            MovieDto deletedMovieDto = new MovieDto(movie.getTitle(), movie.getGenre(), movie.getDuration());
            return Optional.of(deletedMovieDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MovieDto> listMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> new MovieDto(movie.getTitle(), movie.getGenre(), movie.getDuration()))
                .collect(Collectors.toList());
    }
}
