package com.epam.training.ticketservice.core.movie.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
@Getter
@Setter
@Data
@Value
public class MovieDto {
    private final String title;
    private  final String genre;
    private final int duration;


}
