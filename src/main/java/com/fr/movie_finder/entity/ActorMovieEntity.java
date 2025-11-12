package com.fr.movie_finder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "actor_movie")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ActorMovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private ActorEntity actor;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    public ActorMovieEntity(ActorEntity actor, MovieEntity movie) {
        this.actor = actor;
        this.movie = movie;
    }
}
