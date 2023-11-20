package com.epam.training.ticketservice.core.movie.model;

import lombok.Builder;
import lombok.Value;

@Value
public class MovieDto {
    private final String title;
    private  final String genre;
    private final int duration;


}
