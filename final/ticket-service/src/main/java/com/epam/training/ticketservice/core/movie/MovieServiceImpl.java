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
        Movie movie = new Movie(title, title, duration);
        movieRepository.save(movie);
    }

    @Override
    public Optional<MovieDto> updateMovie(String title, String genre, Integer duration) {
        return Optional.empty();
    }

    @Override
    public Optional<MovieDto> deleteMovie(String title) {
        return Optional.empty();
    }
    @Override
    public List<MovieDto> listMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> new MovieDto(movie.getTitle(), movie.getGenre(), movie.getDuration()))
                .collect(Collectors.toList());
    }
}
