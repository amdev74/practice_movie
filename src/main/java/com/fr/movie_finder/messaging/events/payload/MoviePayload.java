package com.fr.movie_finder.messaging.events.payload;

import com.fr.movie_finder.entity.Genre;
import com.fr.movie_finder.messaging.events.MovieReleaseEvent;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MoviePayload {
    private String name;
    private Genre genre;
    private LocalDate publicationDate;
    private List<ActorPayload> actors;
}