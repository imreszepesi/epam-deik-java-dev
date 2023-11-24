package com.epam.training.ticketservice.core.screen;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screen.model.ScreenDto;
import com.epam.training.ticketservice.core.screen.persistence.Screen;
import com.epam.training.ticketservice.core.screen.persistence.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;

    @Override
    public String createScreening(ScreenDto screenDto) {
        Screen screen = new Screen();
        if (screenOverlapping(screenDto)) {
            return "There is an overlapping screen";
        }
        if (breakPeriod(screenDto)){
            return "This would start in the break period after another screen in this room";
        }
        screen.setRoom(screenDto.getRoom());
        screen.setMovie(screenDto.getMovie());
        screen.setScreeningDate(screenDto.getScreeningDate());
        screen.setScreeningEndDate(screenDto.getScreeningEndDate());
        screenRepository.save(screen);
        return screen.toString();
    }

    private boolean breakPeriod(ScreenDto screenDto) {
        return screenRepository
                .findByScreeningEndDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                        screenDto.getScreeningDate().minusSeconds(10*60),
                        screenDto.getScreeningDate(),
                        screenDto.getRoom().getName())
                .isPresent();
    }

    @Override
    public boolean screenOverlapping(ScreenDto screenDto){
        return screenRepository
                .findByScreeningDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                        screenDto.getScreeningDate(), screenDto.getScreeningEndDate(), screenDto.getRoom().getName())
                .isPresent()
                || screenRepository
                .findByScreeningDateGreaterThanEqualAndScreeningDateLessThanEqualAndRoom_Name(
                        screenDto.getScreeningDate(), screenDto.getScreeningEndDate(), screenDto.getRoom().getName())
                .isPresent()
                || screenRepository
                .findByScreeningEndDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                        screenDto.getScreeningDate(), screenDto.getScreeningEndDate(), screenDto.getRoom().getName())
                .isPresent()
                || screenRepository
                .findByScreeningDateLessThanEqualAndScreeningEndDateGreaterThanEqualAndRoom_Name(
                        screenDto.getScreeningDate(), screenDto.getScreeningEndDate(), screenDto.getRoom().getName())
                .isPresent();
    }

    @Override
    public List<ScreenDto> getScreeningList() {
        return screenRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteScreening(String movieName, String roomName, LocalDateTime screenDate) {
        screenRepository.deleteByMovie_NameAndRoom_NameAndScreeningDate(movieName, roomName, screenDate);
    }

    public ScreenDto entityToDto(Screen screen){
        return ScreenDto.builder()
                .movie(screen.getMovie())
                .room(screen.getRoom())
                .screeningDate(screen.getScreeningDate())
                .screeningEndDate(screen.getScreeningEndDate())
                .build();
    }
}
