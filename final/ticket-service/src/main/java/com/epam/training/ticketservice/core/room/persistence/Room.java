package com.epam.training.ticketservice.core.room.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;

@Entity
@Table(name = "Rooms")
@Data
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    private Integer numRows;
    private Integer numColumns;

    public Room(String name, Integer numRows, Integer numColumns) {
        this.name = name;
        this.numRows = numRows;
        this.numColumns = numColumns;
    }
}
