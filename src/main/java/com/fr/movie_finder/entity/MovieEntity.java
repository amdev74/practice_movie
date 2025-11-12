package com.fr.movie_finder.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "movies",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "genre", "publication_date"})
)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Genre genre;

    private LocalDate publicationDate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActorMovieEntity> actors;

    public MovieEntity(String name, Genre genre, LocalDate publicationDate, List<ActorMovieEntity> actors) {
        this.name = name;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.actors = actors;
    }
}
