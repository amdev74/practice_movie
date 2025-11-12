package com.fr.movie_finder.service;

import com.fr.movie_finder.dto.MovieDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface MovieService {

    @Transactional
    List<MovieDTO> getAllMovies();

    MovieDTO getMovieByName(String name) throws EntityNotFoundException;
    List<MovieDTO> getMoviesByStartDateAndEndDate(LocalDate startDate, LocalDate endDate) throws EntityNotFoundException;;
    MovieDTO createMovie(MovieDTO movie) throws MethodArgumentNotValidException;
}
