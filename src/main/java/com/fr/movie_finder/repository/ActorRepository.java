package com.fr.movie_finder.repository;

import com.fr.movie_finder.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
    Optional<ActorEntity> findFirstByFirstnameAndLastname(String firstName, String lastName);
}
