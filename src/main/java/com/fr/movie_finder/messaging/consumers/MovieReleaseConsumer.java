package com.fr.movie_finder.messaging.consumers;

import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.messaging.events.MovieReleaseEvent;
import com.fr.movie_finder.messaging.events.mapper.MovieEventMapper;
import com.fr.movie_finder.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class MovieReleaseConsumer {

    Logger logger = LoggerFactory.getLogger(MovieReleaseConsumer.class);

    private final MovieEventMapper movieEventMapper;
    private final MovieService movieService;

    public MovieReleaseConsumer(MovieEventMapper movieEventMapper, MovieService movieService) {
        this.movieEventMapper = movieEventMapper;
        this.movieService = movieService;
    }

    @KafkaListener(topics = "movie-releases", groupId = "movie-finder-group")
    public void consume(MovieReleaseEvent event) throws MethodArgumentNotValidException {
        logger.info("Consume event movie-release with payload : {}", event.getPayload());
        MovieDTO movie = movieEventMapper.toDTO(event.getPayload());
        movieService.createMovie(movie);
    }
}

