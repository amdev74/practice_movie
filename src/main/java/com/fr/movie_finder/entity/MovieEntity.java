package com.fr.movie_finder.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@ToString
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
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
