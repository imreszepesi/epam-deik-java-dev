package com.epam.training.ticketservice.core.screen;

import com.epam.training.ticketservice.core.screen.model.ScreenDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreenService {
    String createScreening(ScreenDto screeningDto);

    boolean screenOverlapping(ScreenDto screeningDto);

    List<ScreenDto> getScreeningList();

    void deleteScreening(String movieName, String roomName, LocalDateTime screeningDate);
}


