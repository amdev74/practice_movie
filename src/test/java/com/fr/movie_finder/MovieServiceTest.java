package com.fr.movie_finder;

import com.fr.movie_finder.dto.ActorDTO;
import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.entity.Genre;
import com.fr.movie_finder.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MovieServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceTests.class);

    @Autowired
    private MovieService movieService;

    private MovieDTO movieSw;

    @BeforeEach
    void setup() throws Exception {
        ActorDTO actor1 = new ActorDTO("Mark", "Hamill");
        ActorDTO actor2 = new ActorDTO("Harrison", "Ford");
        Date publicationDate = Date.from(LocalDate.of(1977, 5, 25)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        movieSw = new MovieDTO("Un nouvel espoir", Genre.SF, publicationDate, List.of(actor1, actor2));
        movieService.createMovie(movieSw);

        ActorDTO actor3 = new ActorDTO("Carry", "Fisher");

        Date publicationDateV = Date.from(LocalDate.of(1980, 5, 25)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        movieSw = new MovieDTO("L'empire contre-attaque", Genre.SF, publicationDateV, List.of(actor1, actor2, actor3));
        movieService.createMovie(movieSw);
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
        List<MovieDTO> retrieved = movieService.getAllMovies();

        retrieved.forEach(movie -> logger.info(String.valueOf(movie)));

        assertThat(retrieved.size()).isEqualTo(2);
    }

    @Test
    void testGetMoviesBetweenDate() {
        Date start = Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());

        Date end = Date.from(LocalDate.of(1990, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());

        List<MovieDTO> retrieved = movieService.getMoviesByStartDateAndEndDate(start, end);

        retrieved.forEach(movie -> logger.info(String.valueOf(movie)));

        assertThat(retrieved.size()).isEqualTo(2);
    }
}
