package com.epam.training.ticketservice.core.screen.model;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
@Builder
public class ScreenDto {

    private Room room;
    private Movie movie;
    private LocalDateTime screeningDate;
    private LocalDateTime screeningEndDate;

}
