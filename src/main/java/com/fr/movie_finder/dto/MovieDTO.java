package com.fr.movie_finder.dto;

import com.fr.movie_finder.entity.Genre;
import com.fr.movie_finder.entity.MovieEntity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record MovieDTO(Long id,

                       @NotNull(message = "Name must not be null")
                       @NotEmpty(message = "Name must not be empty")
                       String name,

                       @NotNull(message = "Genre must not be null")
                       Genre genre,

                       @NotNull(message = "Publication date must not be null")
                       Date publicationDate,

                       @NotNull(message = "Actors list must not be null")
                       @NotEmpty(message = "Actors list must not be empty")
                       @Valid
                       List<ActorDTO> actors) {
    public MovieDTO(MovieEntity entity) {
        this(entity.getId(),
                entity.getName(),
                entity.getGenre(),
                entity.getPublicationDate(),
                entity.getActors().stream().map(actorMovieEntity -> new ActorDTO(actorMovieEntity.getActor())).collect(Collectors.toList()));
    }

    public MovieDTO(String name, Genre genre, Date publicationDate, List<ActorDTO> actors) {
        this(null, name, genre, publicationDate, actors);
    }
}
