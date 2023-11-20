package com.epam.training.ticketservice.core.movie.persistence;

import com.epam.training.ticketservice.core.user.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
