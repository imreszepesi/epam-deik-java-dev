package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.movie.MovieServiceImpl;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMovie() {
        // Arrange
        String title = "Movie1";
        String genre = "Action";
        int duration = 120;

        // Act
        movieService.createMovie(title, genre, duration);

        // Assert
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testUpdateMovie() {
        // Arrange
        String title = "Movie1";
        String genre = "Action";
        int duration = 120;
        Movie existingMovie = new Movie(title, genre, duration);

        when(movieRepository.findByTitle(title)).thenReturn(Optional.of(existingMovie));

        // Act
        Optional<MovieDto> updatedMovieDto = movieService.updateMovie(title, "Comedy", 90);

        // Assert
        assertTrue(updatedMovieDto.isPresent());
        assertEquals("Comedy", updatedMovieDto.get().getGenre());
        assertEquals(90, updatedMovieDto.get().getDuration());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testUpdateMovieNonExistent() {
        // Arrange
        String title = "NonExistentMovie";
        String genre = "Action";
        int duration = 120;

        when(movieRepository.findByTitle(title)).thenReturn(Optional.empty());

        // Act
        Optional<MovieDto> updatedMovieDto = movieService.updateMovie(title, genre, duration);

        // Assert
        assertTrue(updatedMovieDto.isEmpty());
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void testDeleteMovie() {
        // Arrange
        String title = "MovieToDelete";
        String genre = "Action";
        int duration = 120;
        Movie existingMovie = new Movie(title, genre, duration);

        when(movieRepository.findByTitle(title)).thenReturn(Optional.of(existingMovie));

        // Act
        Optional<MovieDto> deletedMovieDto = movieService.deleteMovie(title);

        // Assert
        assertTrue(deletedMovieDto.isPresent());
        assertEquals(title, deletedMovieDto.get().getTitle());
        verify(movieRepository, times(1)).delete(any(Movie.class));
    }

    @Test
    void testDeleteMovieNonExistent() {
        // Arrange
        String title = "NonExistentMovie";

        when(movieRepository.findByTitle(title)).thenReturn(Optional.empty());

        // Act
        Optional<MovieDto> deletedMovieDto = movieService.deleteMovie(title);

        // Assert
        assertTrue(deletedMovieDto.isEmpty());
        verify(movieRepository, never()).delete(any(Movie.class));
    }

    @Test
    void testListMovies() {
        // Arrange
        when(movieRepository.findAll()).thenReturn(Collections.singletonList(new Movie("Movie1", "Action", 120)));

        // Act
        List<MovieDto> movieDtoList = movieService.listMovies();

        // Assert
        assertNotNull(movieDtoList);
        assertFalse(movieDtoList.isEmpty());
        verify(movieRepository, times(1)).findAll();
    }
}

