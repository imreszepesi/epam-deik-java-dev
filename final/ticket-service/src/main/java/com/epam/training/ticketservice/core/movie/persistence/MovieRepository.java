package com.epam.training.ticketservice.core.movie.persistence;

import com.epam.training.ticketservice.core.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByTitle(String title);
}
