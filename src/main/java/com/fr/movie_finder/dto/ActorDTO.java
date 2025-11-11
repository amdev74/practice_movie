package com.fr.movie_finder.dto;

import com.fr.movie_finder.entity.ActorEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ActorDTO(
        Long id,

        @NotNull(message = "Actor first name must not be null")
        @NotEmpty(message = "Actor first name must not be null")
        String firstname,

        @NotNull(message = "Actor last name must not be null")
        @NotEmpty(message = "Actor last name must not be null")
        String lastname) {

    public ActorDTO(ActorEntity actorEntity) {
        this(actorEntity.getId(),
                actorEntity.getFirstname(),
                actorEntity.getLastname());
    }
}
