package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screen.model.ScreenDto;
import com.epam.training.ticketservice.core.screen.ScreenService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ScreenCommand {

    private final ScreenService screenService;
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private LocalDateTime date;

    @ShellMethod(key = "create screening", value = "creating a screening in a cinema room")
    public String createScreening(String movieName, String roomName, String screeningTime) throws ParseException {
        date = LocalDateTime.parse(screeningTime, format);
        ScreenDto screeningDto = ScreenDto.builder()
                .title(movieRepository.findByTitle(movieName).get())
                .room(roomRepository.findByName(roomName).get())
                .screeningDate(date)
                .screeningEndDate(date.plusSeconds(movieRepository.findByTitle(movieName).get().getDuration() * 60L))
                .build();
        return screenService.registerScreen(screeningDto);
    }

    @ShellMethod(key = "list screenings", value = "Shows all the existing screening")
    public String listScreening() {
        if (screenService.listScreens().isEmpty()) {
            return "There are no screenings";
        }

        return screenService.listScreens()
                .stream()
                .map(screen -> String.format("%s (%s, %d minutes), screened in room %s, at %s",
                        screen.getTitle().getTitle(),
                        screen.getTitle().getGenre(),
                        screen.getTitle().getDuration(),
                        screen.getRoom().getName(),
                        screen.getScreeningDate().format(format)))
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "delete screening", value = "delete specific screening")
    public String deleteScreening(String movieName, String roomName, String screeningDate) {
        try {
            date = LocalDateTime.parse(screeningDate, format);
            screenService.removeScreen(movieName, roomName, date);
            return "Screening deleted successfully";
        } catch (Exception e) {
            return "Error deleting screening: " + e.getMessage();
        }
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().getRole() != User.Role.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
