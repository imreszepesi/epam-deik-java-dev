package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.room.RoomServiceImpl;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
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

class RoomTest{

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoom() {
        // Arrange
        String roomName = "Room1";
        int numRows = 5;
        int numColumns = 8;

        // Act
        roomService.createRoom(roomName, numRows, numColumns);

        // Assert
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testUpdateRoom() {
        // Arrange
        String roomName = "Room1";
        int numRows = 5;
        int numColumns = 8;
        Room existingRoom = new Room(roomName, numRows, numColumns);

        when(roomRepository.findByName(roomName)).thenReturn(Optional.of(existingRoom));

        // Act
        Optional<RoomDto> updatedRoomDto = roomService.updateRoom(roomName, numRows + 1, numColumns + 1);

        // Assert
        assertTrue(updatedRoomDto.isPresent());
        assertEquals(numRows + 1, updatedRoomDto.get().getNumRows());
        assertEquals(numColumns + 1, updatedRoomDto.get().getNumColumns());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testUpdateRoomNonExistent() {
        // Arrange
        String roomName = "NonExistentRoom";
        int numRows = 5;
        int numColumns = 8;

        when(roomRepository.findByName(roomName)).thenReturn(Optional.empty());

        // Act
        Optional<RoomDto> updatedRoomDto = roomService.updateRoom(roomName, numRows, numColumns);

        // Assert
        assertTrue(updatedRoomDto.isEmpty());
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testDeleteRoom() {
        // Arrange
        String roomName = "RoomToDelete";
        int numRows = 5;
        int numColumns = 8;
        Room existingRoom = new Room(roomName, numRows, numColumns);

        when(roomRepository.findByName(roomName)).thenReturn(Optional.of(existingRoom));

        // Act
        Optional<RoomDto> deletedRoomDto = roomService.deleteRoom(roomName);

        // Assert
        assertTrue(deletedRoomDto.isPresent());
        assertEquals(roomName, deletedRoomDto.get().getName());
        verify(roomRepository, times(1)).delete(any(Room.class));
    }

    @Test
    void testDeleteRoomNonExistent() {
        // Arrange
        String roomName = "NonExistentRoom";

        when(roomRepository.findByName(roomName)).thenReturn(Optional.empty());

        // Act
        Optional<RoomDto> deletedRoomDto = roomService.deleteRoom(roomName);

        // Assert
        assertTrue(deletedRoomDto.isEmpty());
        verify(roomRepository, never()).delete(any(Room.class));
    }

    @Test
    void testListRooms() {
        // Arrange
        when(roomRepository.findAll()).thenReturn(Collections.singletonList(new Room("Room1", 5, 8)));

        // Act
        List<RoomDto> roomDtoList = roomService.listRooms();

        // Assert
        assertNotNull(roomDtoList);
        assertFalse(roomDtoList.isEmpty());
        verify(roomRepository, times(1)).findAll();
    }
}

