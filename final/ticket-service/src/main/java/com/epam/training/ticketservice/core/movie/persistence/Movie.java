package com.epam.training.ticketservice.core.movie.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Movies")
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String title;
    private String genre;
    private Integer duration;


    public Movie(String title, String genre, Integer duration) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

}

