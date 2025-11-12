package com.fr.movie_finder.messaging.events.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ActorPayload {
    private String firstname;
    private String lastname;
}