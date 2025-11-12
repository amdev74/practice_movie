package com.fr.movie_finder.messaging.events.payload;

import com.fr.movie_finder.entity.Genre;
import com.fr.movie_finder.messaging.events.MovieReleaseEvent;
import lombok.*;

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
    private Date publicationDate;
    private List<ActorPayload> actors;
}