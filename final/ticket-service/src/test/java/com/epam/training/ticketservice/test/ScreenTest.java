package com.epam.training.ticketservice.test;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.screen.ScreenServiceImpl;
import com.epam.training.ticketservice.core.screen.model.ScreenDto;
import com.epam.training.ticketservice.core.screen.persistence.Screen;
import com.epam.training.ticketservice.core.screen.persistence.ScreenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScreenTest{

    @Mock
    private ScreenRepository screenRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ScreenServiceImpl screenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterScreen() {
        // Arrange
        Room room = new Room();
        Movie movie = new Movie();

        ScreenDto screenDto = ScreenDto.builder()
                .room(room)
                .title(movie)
                .screeningDate(LocalDateTime.now())
                .screeningEndDate(LocalDateTime.now().plusHours(2))
                .build();

        when(screenRepository.findByScreeningDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                any(LocalDateTime.class), any(LocalDateTime.class), anyString())).thenReturn(Optional.empty());
        when(screenRepository.findByScreeningEndDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                any(LocalDateTime.class), any(LocalDateTime.class), anyString())).thenReturn(Optional.empty());
        when(screenRepository.save(any(Screen.class))).thenReturn(new Screen());

        // Act
        String result = screenService.registerScreen(screenDto);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Movie Title"));
        verify(screenRepository, times(1)).save(any(Screen.class));
    }

    @Test
    void testRegisterScreenWithOverlap() {
        // Arrange
        Room room = new Room();
        Movie movie = new Movie();
        ScreenDto screenDto = ScreenDto.builder()
                .room(room)
                .title(movie)
                .screeningDate(LocalDateTime.now())
                .screeningEndDate(LocalDateTime.now().plusHours(2))
                .build();

        when(screenRepository.findByScreeningDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                any(LocalDateTime.class), any(LocalDateTime.class), anyString())).thenReturn(Optional.of(new Screen()));

        // Act
        String result = screenService.registerScreen(screenDto);

        // Assert
        assertEquals("There is an overlapping screening", result);

        // Verify that the save method is never called when there is an overlap
        verify(screenRepository, never()).save(any(Screen.class));
    }




    @Test
    void testListScreens() {
        // Arrange
        when(screenRepository.findAll()).thenReturn(Collections.singletonList(new Screen()));

        // Act
        List<ScreenDto> result = screenService.listScreens();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(screenRepository, times(1)).findAll();
    }

    @Test
    void testRemoveScreen() {
        // Arrange
        Movie movie = new Movie();
        when(movieRepository.findByTitle(anyString())).thenReturn(Optional.of(movie));
        doNothing().when(screenRepository).deleteByTitleAndRoom_NameAndScreeningDate(
                any(Movie.class), anyString(), any(LocalDateTime.class));

        // Act
        assertDoesNotThrow(() -> screenService.removeScreen("Movie Title", "RoomName", LocalDateTime.now()));

        // Assert
        verify(movieRepository, times(1)).findByTitle(anyString());
        verify(screenRepository, times(1)).deleteByTitleAndRoom_NameAndScreeningDate(
                any(Movie.class), anyString(), any(LocalDateTime.class));
    }
}
