package com.fr.movie_finder.service;

import com.fr.movie_finder.dto.MovieDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {

    @Transactional
    List<MovieDTO> findAllMovies();

    MovieDTO getMovieByName(String name) throws EntityNotFoundException;

    MovieDTO createMovie(MovieDTO movie);

    @Transactional
    MovieDTO updateMovie(Long id, MovieDTO movieDTO);

    @Transactional
    void deleteMovie(Long id);

    List<MovieDTO> searchMovies(String name, LocalDate startDate, LocalDate endDate);
}
