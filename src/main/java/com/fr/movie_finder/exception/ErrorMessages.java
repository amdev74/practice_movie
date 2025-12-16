package com.fr.movie_finder.exception;

import lombok.Value;

@Value
public class ErrorMessages {

    public static final String MOVIE_NOT_FOUND = "Movie not found";
    public static final String MOVIE_ALREADY_EXIST = "Movie already exist";

    public static final String ACTOR_NOT_FOUND = "Actor not found";
    public static final String GENRE_NOT_FOUND = "Genre not found";
}