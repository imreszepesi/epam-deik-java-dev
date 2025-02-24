package com.epam.training.ticketservice.core.screen.persistence;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;




@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    Optional<Screen> findByScreeningDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
            LocalDateTime screeningDate, LocalDateTime screeningEndDate, String name);

    Optional<Screen> findByScreeningDateGreaterThanEqualAndScreeningDateLessThanEqualAndRoom_Name(
            LocalDateTime screeningDate, LocalDateTime screeningEndDate, String name);

    Optional<Screen> findByScreeningEndDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
            LocalDateTime screeningEndDate, LocalDateTime screeningEndDate1, String name);

    Optional<Screen> findByScreeningDateLessThanEqualAndScreeningEndDateGreaterThanEqualAndRoom_Name(
            LocalDateTime screeningDate, LocalDateTime screeningEndDate, String name);

    Optional<Screen> findByScreeningEndDateGreaterThanEqual(LocalDateTime screeningEndDate);

    @Transactional
    void deleteByTitleAndRoom_NameAndScreeningDate(Movie title, String name1, LocalDateTime screeningDate);

}







