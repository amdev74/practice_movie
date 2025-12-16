package com.fr.movie_finder.dto;

import jakarta.validation.constraints.NotBlank;

public record ActorDTO(
        Long id,

        @NotBlank(message = "Actor first name is required")
        String firstname,

        @NotBlank(message = "Actor last name is required")
        String lastname) {

    public ActorDTO(String firstname, String lastname) {
        this(null, firstname, lastname);
    }
}
