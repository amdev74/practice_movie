package com.fr.movie_finder.dto;

import com.fr.movie_finder.entity.ActorEntity;
import jakarta.validation.constraints.NotBlank;

public record ActorDTO(
        Long id,

        @NotBlank(message = "Actor first name is required")
        String firstname,

        @NotBlank(message = "Actor last name is required")
        String lastname) {

    public ActorDTO(ActorEntity actorEntity) {
        this(actorEntity.getId(),
                actorEntity.getFirstname(),
                actorEntity.getLastname());
    }

    public ActorDTO(String firstname, String lastname) {
        this(null, firstname, lastname);
    }
}
