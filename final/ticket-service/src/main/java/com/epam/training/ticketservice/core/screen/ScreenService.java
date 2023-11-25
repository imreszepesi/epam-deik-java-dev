package com.epam.training.ticketservice.core.screen;

import com.epam.training.ticketservice.core.screen.model.ScreenDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreenService {
    String registerScreen(ScreenDto screeningDto);

    boolean checkForOverlap(ScreenDto screeningDto);

    List<ScreenDto> listScreens();

    void removeScreen(String movieName, String roomName, LocalDateTime screeningDate);
}


