package com.epam.training.ticketservice.core.room;



import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements  RoomService{
    private final RoomRepository roomRepository;

    @Override
    public void createRoom(String name, Integer numSeats, Integer numRows, Integer numColumns) {
        Room room = new Room(name,numSeats,numRows,numColumns);
        roomRepository.save(room);
    }

    @Override
    public Optional<RoomDto> updateRoom(String name, Integer numSeats, Integer numRows, Integer numColumns) {
        Optional<Room> optionalRoom = roomRepository.findByName(name);
        if(optionalRoom.isPresent()){
            Room room = optionalRoom.get();
            room.setNumColumns(numColumns);
            room.setNumRows(numRows);
            room.setNumSeats(numSeats);
            RoomDto updateRoomDto = new RoomDto(room.getName(),room.getNumSeats(),room.getNumRows(),room.getNumColumns());
            return Optional.of(updateRoomDto);
        }
        else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<RoomDto> deleteRoom(String name) {
        Optional<Room> optionalRoom = roomRepository.findByName(name);

        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            roomRepository.delete(room);

            RoomDto deletedRoomDto = new RoomDto(room.getName(), room.getNumSeats(), room.getNumRows(), room.getNumColumns());
            return Optional.of(deletedRoomDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<RoomDto> listRooms() {
        return roomRepository.findAll().stream()
                .map(room -> new RoomDto(room.getName(), room.getNumSeats(), room.getNumRows(), room.getNumColumns()))
                .collect(Collectors.toList());
    }
}
