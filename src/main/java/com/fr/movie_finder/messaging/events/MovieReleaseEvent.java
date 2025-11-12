package com.fr.movie_finder.messaging.events;

import com.fr.movie_finder.entity.Genre;
import com.fr.movie_finder.messaging.events.payload.MoviePayload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReleaseEvent {
    private String eventId;
    private String timestamp;
    private String source;
    private MoviePayload payload;
}