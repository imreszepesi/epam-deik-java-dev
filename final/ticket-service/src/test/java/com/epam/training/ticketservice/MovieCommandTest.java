package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.ui.command.MovieCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MovieCommandTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieCommand movieCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void updateMovie_ShouldReturnSuccessMessage_WhenMovieUpdateIsSuccessful() {
        // Arrange
        when(movieService.updateMovie(any(), any(), any())).thenReturn(Optional.of(new MovieDto("Updated Movie", "Drama", 150)));

        // Act
        String result = movieCommand.updateMovie("Test Movie", "Action", 120);

        // Assert
        assertEquals("Update movie was successful: Updated Movie (Drama, 150 minutes)", result);
    }

    @Test
    void updateMovie_ShouldReturnFailureMessage_WhenMovieUpdateFails() {
        // Arrange
        when(movieService.updateMovie(any(), any(), any())).thenReturn(Optional.empty());

        // Act
        String result = movieCommand.updateMovie("Test Movie", "Action", 120);

        // Assert
        assertEquals("Update movie failed! Movie not found.", result);
    }

    @Test
    void deleteMovie_ShouldReturnSuccessMessage_WhenMovieDeletionIsSuccessful() {
        // Arrange
        when(movieService.deleteMovie(any())).thenReturn(Optional.of(new MovieDto("Deleted Movie", "Comedy", 90)));

        // Act
        String result = movieCommand.deleteMovie("Test Movie");

        // Assert
        assertEquals("Delete movie was successful: Deleted Movie (Comedy, 90 minutes)", result);
    }

    @Test
    void deleteMovie_ShouldReturnFailureMessage_WhenMovieDeletionFails() {
        // Arrange
        when(movieService.deleteMovie(any())).thenReturn(Optional.empty());

        // Act
        String result = movieCommand.deleteMovie("Test Movie");

        // Assert
        assertEquals("Delete movie failed! Movie not found.", result);
    }
}


