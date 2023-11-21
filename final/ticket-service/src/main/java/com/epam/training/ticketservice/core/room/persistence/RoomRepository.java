package com.epam.training.ticketservice.core.room.persistence;


import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    Optional<Room> findByTitle(String name);
}
