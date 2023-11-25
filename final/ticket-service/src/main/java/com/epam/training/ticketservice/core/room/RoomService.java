package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    void createRoom(String name,Integer numRows,Integer numColumns);
    Optional<RoomDto> updateRoom(String name, Integer numRows, Integer numColumns);

    Optional<RoomDto> deleteRoom(String name);

    List<RoomDto> listRooms();

}
