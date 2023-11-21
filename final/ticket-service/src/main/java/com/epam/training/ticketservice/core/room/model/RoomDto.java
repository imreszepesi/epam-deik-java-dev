package com.epam.training.ticketservice.core.room.model;

import lombok.Value;

@Value
public class RoomDto {
    private final String name;
    private final int numSeats;
    private final int numRows;
    private final int numColumns;
}
