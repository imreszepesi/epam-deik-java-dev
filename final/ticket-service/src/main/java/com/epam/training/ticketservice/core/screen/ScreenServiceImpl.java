package com.epam.training.ticketservice.core.screen;
import com.epam.training.ticketservice.core.screen.model.ScreenDto;
import com.epam.training.ticketservice.core.screen.persistence.Screen;
import com.epam.training.ticketservice.core.screen.persistence.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;

    @Override
    public String registerScreen(ScreenDto screenDto) {
        if (checkForOverlap(screenDto)) {
            return "There is an overlapping screen";
        }

        if (isBreakPeriod(screenDto)) {
            return "This would start in the break period after another screen in this room";
        }

        Screen screen = createScreenFromDto(screenDto);
        screenRepository.save(screen);

        return screen.toString();
    }
    private Screen createScreenFromDto(ScreenDto screenDto) {
        Screen screen = new Screen();
        screen.setRoom(screenDto.getRoom());
        screen.setTitle(screenDto.getTitle());
        screen.setScreeningDate(screenDto.getScreeningDate());
        screen.setScreeningEndDate(screenDto.getScreeningEndDate());
        return screen;
    }
    private boolean isBreakPeriod(ScreenDto screenDto) {
        return screenRepository
                .findByScreeningEndDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                        screenDto.getScreeningDate().minusSeconds(10*60),
                        screenDto.getScreeningDate(),
                        screenDto.getRoom().getName())
                .isPresent();
    }

    @Override
    public boolean checkForOverlap(ScreenDto screenDto){
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
    public List<ScreenDto> listScreens() {
        return screenRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeScreen(String movieName, String roomName, LocalDateTime screenDate) {
        screenRepository.deleteByTitleAndRoom_NameAndScreeningDate(movieName, roomName, screenDate);
    }

    public ScreenDto convertToDto(Screen screen){
        return ScreenDto.builder()
                .title(screen.getTitle())
                .room(screen.getRoom())
                .screeningDate(screen.getScreeningDate())
                .screeningEndDate(screen.getScreeningEndDate())
                .build();
    }

}
