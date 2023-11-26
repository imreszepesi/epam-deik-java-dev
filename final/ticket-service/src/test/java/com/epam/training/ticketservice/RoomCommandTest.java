package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.ui.command.RoomCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoomCommandTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomCommand roomCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRoom_ShouldReturnSuccessMessage_WhenRoomCreationIsSuccessful() {
        // Arrange
        doNothing().when(roomService).createRoom(any(), any(), any());

        // Act
        String result = roomCommand.createRoom("Room1", 5, 8);

        // Assert
        assertEquals("Create room was successful!", result);
    }

    @Test
    void createRoom_ShouldReturnFailureMessage_WhenRoomCreationFails() {
        // Arrange
        doThrow(new RuntimeException()).when(roomService).createRoom(any(), any(), any());

        // Act
        String result = roomCommand.createRoom("Room1", 5, 8);

        // Assert
        assertEquals("Create room failed!", result);
    }

    @Test
    void listRooms_ShouldReturnNoRoomsMessage_WhenRoomListIsEmpty() {
        // Arrange
        when(roomService.listRooms()).thenReturn(Collections.emptyList());

        // Act
        String result = roomCommand.listRooms();

        // Assert
        assertEquals("There are no rooms at the moment", result);
    }



    @Test
    void updateRoom_ShouldReturnSuccessMessage_WhenRoomUpdateIsSuccessful() {
        // Arrange
        when(roomService.updateRoom(any(), any(), any())).thenReturn(Optional.of(new RoomDto("UpdatedRoom", 6, 10)));

        // Act
        String result = roomCommand.updateRoom("Room1", 6, 10);

        // Assert
        assertEquals("Update room was successful: UpdatedRoom with 60 seats, 6 rows and 10 columns", result);
    }

    @Test
    void updateRoom_ShouldReturnFailureMessage_WhenRoomUpdateFails() {
        // Arrange
        when(roomService.updateRoom(any(), any(), any())).thenReturn(Optional.empty());

        // Act
        String result = roomCommand.updateRoom("Room1", 6, 10);

        // Assert
        assertEquals("Update room failed! Room not found.", result);
    }

    @Test
    void deleteRoom_ShouldReturnSuccessMessage_WhenRoomDeletionIsSuccessful() {
        // Arrange
        when(roomService.deleteRoom(any())).thenReturn(Optional.of(new RoomDto("DeletedRoom", 7, 12)));

        // Act
        String result = roomCommand.deleteRoom("Room1");

        // Assert
        assertEquals("Delete room was successful: DeletedRoom with 84 seats, 7 rows and 12 columns", result);
    }

    @Test
    void deleteRoom_ShouldReturnFailureMessage_WhenRoomDeletionFails() {
        // Arrange
        when(roomService.deleteRoom(any())).thenReturn(Optional.empty());

        // Act
        String result = roomCommand.deleteRoom("Room1");

        // Assert
        assertEquals("Delete room failed! Room not found.", result);
    }
}

