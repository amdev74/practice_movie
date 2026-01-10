package com.fr.movie_finder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "actor_movie")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActorMovieEntity other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
