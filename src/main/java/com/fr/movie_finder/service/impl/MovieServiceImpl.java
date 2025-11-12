package com.fr.movie_finder.service.impl;

import com.fr.movie_finder.dto.ActorDTO;
import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.entity.ActorEntity;
import com.fr.movie_finder.entity.ActorMovieEntity;
import com.fr.movie_finder.entity.MovieEntity;
import com.fr.movie_finder.exception.AlreadyExistsException;
import com.fr.movie_finder.mapper.MovieMapper;
import com.fr.movie_finder.repository.ActorRepository;
import com.fr.movie_finder.repository.MovieRepository;
import com.fr.movie_finder.service.MovieService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository,
                            ActorRepository actorRepository,
                            MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.movieMapper = movieMapper;
    }

    @Transactional
    @Override
    public List<MovieDTO> getAllMovies() {
        List<MovieEntity> moviesEntities = movieRepository.findAll();

        return moviesEntities.stream().map(movieMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public MovieDTO getMovieByName(String name) throws EntityNotFoundException {
        Optional<MovieEntity> movieEntity = movieRepository.findFirstByName(name);

        if(movieEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format("Movie %s not found", name));
        }

        return movieMapper.toDTO(movieEntity.get());
    }

    @Override
    @Transactional
    public List<MovieDTO> getMoviesByStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        List<MovieEntity> moviesEntities = movieRepository.findAllByPublicationDateGreaterThanEqualAndPublicationDateLessThanEqual(startDate, endDate);

        return moviesEntities.stream().map(movieMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public MovieDTO createMovie(MovieDTO movie) throws MethodArgumentNotValidException {
        Optional<MovieEntity> checkMovie = movieRepository.findFirstByName(movie.name());

        if(checkMovie.isPresent()) {
            throw new AlreadyExistsException("Movie already exist");
        }

        MovieEntity movieEntity = new MovieEntity(movie.name(), movie.genre(),movie.publicationDate(), new ArrayList<>());
        movieEntity = movieRepository.save(movieEntity);

        for(ActorDTO actor : movie.actors()) {
            Optional<ActorEntity> checkActorEntity = actorRepository.findFirstByFirstnameAndLastname(actor.firstname(),actor.lastname());
            ActorEntity actorEntity;

            if(checkActorEntity.isEmpty()) {
                actorEntity = new ActorEntity(actor.firstname(), actor.lastname(), new ArrayList<>());
                actorEntity = actorRepository.save(actorEntity);
            } else {
                actorEntity = checkActorEntity.get();
            }

            ActorMovieEntity actorMovie = new ActorMovieEntity(actorEntity, movieEntity);
            movieEntity.getActors().add(actorMovie);
            actorEntity.getMovies().add(actorMovie);
        }

        return movieMapper.toDTO(movieEntity);
    }
}
