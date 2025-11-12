package com.fr.movie_finder.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "actors")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class ActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActorMovieEntity> movies;

    public ActorEntity(String firstName, String lastName, List<ActorMovieEntity> movies) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.movies = movies;
    }
}
