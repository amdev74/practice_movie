package com.fr.movie_finder.repository;

import com.fr.movie_finder.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    Optional<MovieEntity> findFirstByName(String name);

    List<MovieEntity> findAllByPublicationDateGreaterThanEqualAndPublicationDateLessThanEqual(LocalDate dateStart, LocalDate dateEnd);
}
