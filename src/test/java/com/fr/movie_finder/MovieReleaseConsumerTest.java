package com.fr.movie_finder;

import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.entity.Genre;
import com.fr.movie_finder.messaging.events.MovieReleaseEvent;
import com.fr.movie_finder.messaging.events.payload.ActorPayload;
import com.fr.movie_finder.messaging.events.payload.MoviePayload;
import com.fr.movie_finder.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@EmbeddedKafka(topics = "movie-releases", partitions = 1)
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "kafka.enabled=true",
        "spring.autoconfigure.exclude="
})
class MovieReleaseConsumerTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private MovieReleaseEvent createTestEvent() {
        List<ActorPayload> actors = List.of(
                new ActorPayload("Tom", "Hanks"),
                new ActorPayload("Matt", "Damon")
        );

        MoviePayload moviePayload = new MoviePayload();
        moviePayload.setName("Saving Private Ryan");
        moviePayload.setGenre(Genre.ACTION);
        moviePayload.setPublicationDate(LocalDate.of(1998, 9, 30));

        moviePayload.setActors(actors);

        MovieReleaseEvent event = new MovieReleaseEvent();
        event.setEventId("test-event-001");
        event.setTimestamp("2025-11-12T10:00:00Z");
        event.setSource("IMDB");
        event.setPayload(moviePayload);

        return event;
    }
    @Test
    void shouldConsumeAndCreateMovie() {
        MovieReleaseEvent event = createTestEvent();
        kafkaTemplate.send("movie-releases", event);

        await().pollDelay(1, TimeUnit.SECONDS)
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    MovieDTO movie = movieService.getMovieByName("Saving Private Ryan");
                    assertThat(movie).isNotNull();
                    assertThat(movie.name()).isEqualTo("Saving Private Ryan");
                    assertThat(movie.genre()).isEqualTo(Genre.ACTION);
                    assertThat(movie.actors()).hasSize(2);
                });
    }
}