package com.fr.movie_finder.dto;

import com.fr.movie_finder.entity.Genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public record MovieDTO(Long id,

                       @NotBlank(message = "name is required")
                       String name,

                       @NotBlank(message = "genre is required")
                       Genre genre,

                       @NotBlank(message = "publication date is required")
                       LocalDate publicationDate,

                       @NotNull(message = "Actors list must not be null")
                       @NotEmpty(message = "Actors list must not be empty")
                       @Valid
                       List<ActorDTO> actors) {
    public MovieDTO(String name, Genre genre, LocalDate publicationDate, List<ActorDTO> actors) {
        this(null, name, genre, publicationDate, actors);
    }
}
