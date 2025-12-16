package com.fr.movie_finder.messaging.events;

import com.fr.movie_finder.messaging.events.payload.MoviePayload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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