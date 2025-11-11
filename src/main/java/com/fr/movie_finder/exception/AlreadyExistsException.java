package com.fr.movie_finder.exception;

import lombok.AllArgsConstructor;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
