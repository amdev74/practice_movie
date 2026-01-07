package com.fr.movie_finder.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "actors")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class ActorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstname;

    @Column(nullable = false, length = 100)
    private String lastname;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActorMovieEntity> movies = new ArrayList<>();

    public ActorEntity(String firstName, String lastName, List<ActorMovieEntity> movies) { this.firstname = firstName; this.lastname = lastName; this.movies = movies; }

    public void addMovie(ActorMovieEntity actorMovie) {
        movies.add(actorMovie);
        actorMovie.setActor(this);
    }

    public void removeMovie(ActorMovieEntity actorMovie) {
        movies.remove(actorMovie);
        actorMovie.setActor(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActorEntity other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
