package com.fr.movie_finder.service.impl;

import com.fr.movie_finder.dto.ActorDTO;
import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.entity.ActorEntity;
import com.fr.movie_finder.entity.ActorMovieEntity;
import com.fr.movie_finder.entity.MovieEntity;
import com.fr.movie_finder.exception.AlreadyExistsException;
import com.fr.movie_finder.exception.ErrorMessages;
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

        return movieRepository.findFirstByName(name)
                .map(movieMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.MOVIE_NOT_FOUND));
    }

    @Override
    @Transactional
    public List<MovieDTO> getMoviesByStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        List<MovieEntity> moviesEntities = movieRepository.findAllByPublicationDateGreaterThanEqualAndPublicationDateLessThanEqual(startDate, endDate);

        return moviesEntities.stream().map(movieMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public MovieDTO createMovie(MovieDTO movie) {
        movieRepository.findFirstByName(movie.name())
                .ifPresent(m -> { throw new AlreadyExistsException(ErrorMessages.MOVIE_ALREADY_EXIST); });

        MovieEntity movieEntity = movieRepository.save(
                new MovieEntity(movie.name(), movie.genre(), movie.publicationDate(), new ArrayList<>())
        );

        movie.actors().stream()
                .map(this::findOrCreateActor)
                .map(actor -> new ActorMovieEntity(actor,movieEntity))
                .forEach(actorMovie -> {
                    movieEntity.getActors().add(actorMovie);
                    actorMovie.getActor().getMovies().add(actorMovie);
                });

        return movieMapper.toDTO(movieEntity);
    }

    private ActorEntity findOrCreateActor(ActorDTO actor) {
        return actorRepository.findFirstByFirstnameAndLastname(actor.firstname(),actor.lastname())
                .orElseGet(() -> actorRepository.save(new ActorEntity(actor.firstname(), actor.lastname(), new ArrayList<>())));
    }

    @Transactional
    @Override
    public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
        MovieEntity existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.MOVIE_NOT_FOUND));

        checkForDuplicateName(movieDTO.name(), id);

        existingMovie.setName(movieDTO.name());
        existingMovie.setGenre(movieDTO.genre());
        existingMovie.setPublicationDate(movieDTO.publicationDate());

        synchronizeActors(existingMovie, movieDTO.actors());

        return movieMapper.toDTO(existingMovie);
    }

    @Transactional
    @Override
    public void deleteMovie(Long id) {
        MovieEntity movie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.MOVIE_NOT_FOUND));

        removeAllActorAssociations(movie);
        movieRepository.delete(movie);
    }

    private void checkForDuplicateName(String name, Long excludedId) {
        movieRepository.findFirstByName(name)
                .filter(existing -> !existing.getId().equals(excludedId))
                .ifPresent(m -> { throw new AlreadyExistsException(ErrorMessages.MOVIE_ALREADY_EXIST); });
    }

    private void synchronizeActors(MovieEntity movie, List<ActorDTO> newActors) {
        // Remove existing associations (both sides of the relationship)
        removeAllActorAssociations(movie);

        // Add new associations
        newActors.stream()
                .map(this::findOrCreateActor)
                .map(actor -> new ActorMovieEntity(actor, movie))
                .forEach(actorMovie -> {
                    movie.getActors().add(actorMovie);
                    actorMovie.getActor().getMovies().add(actorMovie);
                });
    }

    private void removeAllActorAssociations(MovieEntity movie) {
        movie.getActors().forEach(actorMovie ->
                actorMovie.getActor().getMovies().remove(actorMovie));
        movie.getActors().clear();
    }
}

