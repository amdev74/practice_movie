package com.fr.movie_finder;

import com.fr.movie_finder.dto.ActorDTO;
import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.entity.Genre;
import com.fr.movie_finder.service.MovieService;
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

@SpringBootTest
class MovieServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceTest.class);

    @Autowired
    private MovieService movieService;

    @Test
    void createMovieWithActorsAndGet() throws Exception {
        // Create ActorDTOs
        ActorDTO actor1 = new ActorDTO("Mark", "Hamill");
        ActorDTO actor2 = new ActorDTO("Harrison", "Ford");

        LocalDate localDate = LocalDate.of(1977, 5, 25);
        Date publicationDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        MovieDTO movieSw = new MovieDTO("Un nouvel espoir", Genre.SF, publicationDate, List.of(actor1, actor2));

        movieService.createMovie(movieSw);
        MovieDTO createdMovie = movieService.getMovieByName("Un nouvel espoir");

        logger.info("Created movie: {}", createdMovie);

        assertThat(createdMovie).isNotNull();
    }
}