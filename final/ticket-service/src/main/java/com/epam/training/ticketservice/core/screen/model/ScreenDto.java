package com.epam.training.ticketservice.core.screen.model;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScreenDto {
    private Room room;
    private Movie title;
    private LocalDateTime screeningDate;
    private LocalDateTime screeningEndDate;
}
