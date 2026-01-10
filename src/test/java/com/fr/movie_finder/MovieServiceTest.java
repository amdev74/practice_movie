package com.fr.movie_finder;

import com.fr.movie_finder.dto.ActorDTO;
import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.entity.Genre;
import com.fr.movie_finder.exception.AlreadyExistsException;
import com.fr.movie_finder.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

@SpringBootTest
@Transactional
class MovieServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceTest.class);

    @Autowired
    private MovieService movieService;

    private MovieDTO movieSw;

    @BeforeEach
    void setup() throws Exception {
        ActorDTO actor1 = new ActorDTO("Mark", "Hamill");
        ActorDTO actor2 = new ActorDTO("Harrison", "Ford");
        LocalDate publicationDate = LocalDate.of(1977, 5, 25);
        movieSw = new MovieDTO("Un nouvel espoir", Genre.SF, publicationDate, List.of(actor1, actor2));
        movieService.createMovie(movieSw);

        ActorDTO actor3 = new ActorDTO("Carry", "Fisher");

        LocalDate publicationDateV = LocalDate.of(1980, 5, 25);
        movieSw = new MovieDTO("L'empire contre-attaque", Genre.SF, publicationDate, List.of(actor1, actor2, actor3));
        movieService.createMovie(movieSw);
    }

    @Test
    void testCreateMovieShouldReturnAlreadyExist() throws MethodArgumentNotValidException {
        ActorDTO actor1 = new ActorDTO("Mark", "Hamill");
        ActorDTO actor2 = new ActorDTO("Harrison", "Ford");
        LocalDate publicationDate = LocalDate.of(1977, 5, 25);
        MovieDTO movieSwDuplicated = new MovieDTO("Un nouvel espoir", Genre.SF, publicationDate, List.of(actor1, actor2));

        assertThrows(AlreadyExistsException.class, () -> {
            movieService.createMovie(movieSwDuplicated);
        });
    }

    @Test
    void testCreateMovie() {
        assertThat(movieSw).isNotNull();
    }

    @Test
    void testGetMovieByName() {
        MovieDTO retrieved = movieService.getMovieByName("Un nouvel espoir");

        logger.info(String.valueOf(retrieved));

        assertThat(retrieved).isNotNull();
        assertThat(retrieved.name()).isEqualTo("Un nouvel espoir");
    }

    @Test
    void testGetMovies() {
        List<MovieDTO> retrieved = movieService.findAllMovies();

        retrieved.forEach(movie -> logger.info(String.valueOf(movie)));

        assertThat(retrieved.size()).isEqualTo(2);
    }

    @Test
    void testGetMoviesBetweenDate() {
        LocalDate start = LocalDate.of(1970, 1, 1);

        LocalDate end = LocalDate.of(1990, 1, 1);

        List<MovieDTO> retrieved = movieService.findMoviesByStartDateAndEndDate(start, end);

        retrieved.forEach(movie -> logger.info(String.valueOf(movie)));

        assertThat(retrieved.size()).isEqualTo(2);
    }
}
