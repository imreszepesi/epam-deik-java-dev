package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class RoomCommand {
    private final RoomService roomService;

    @ShellMethod(key = "create room", value = "Room Creation")
    public String createRoom(String name, Integer numSeats, Integer numRows, Integer numColumns) {
        try {
            roomService.createRoom(name, numSeats, numRows, numColumns);
            return "Create room was successful!";
        } catch (Exception e) {
            return "Create room failed!";
        }
    }

    @ShellMethod(key = "list rooms", value = "Get rooms")
    public String listRooms() {
        List<RoomDto> rooms = roomService.listRooms();

        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            for (RoomDto room : rooms) {
                result.append(String.format("%s with %d seats, %d rows, and %d columns%n",
                        room.getName(), room.getNumSeats(), room.getNumRows(), room.getNumColumns()));
            }
            return result.toString();
        }
    }

    @ShellMethod(key = "update room", value = "Update an existing room")
    public String updateRoom(String name, Integer numSeats, Integer numRows, Integer numColumns) {
        Optional<RoomDto> updatedRoom = roomService.updateRoom(name, numSeats, numRows, numColumns);
        return updatedRoom.map(room -> "Update room was successful: " + formatRoom(room))
                .orElse("Update room failed! Room not found.");
    }

    @ShellMethod(key = "delete room", value = "Delete an existing room")
    public String deleteRoom(String name) {
        Optional<RoomDto> deletedRoom = roomService.deleteRoom(name);
        return deletedRoom.map(room -> "Delete room was successful: " + formatRoom(room))
                .orElse("Delete room failed! Room not found.");
    }

    private String formatRoom(RoomDto room) {
        return String.format("%s with %d seats, %d rows, and %d columns",
                room.getName(), room.getNumSeats(), room.getNumRows(), room.getNumColumns());
    }
}
