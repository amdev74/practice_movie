package com.fr.movie_finder.repository.specification;

import com.fr.movie_finder.entity.MovieEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@NoArgsConstructor
public final class MovieSpecifications {

    public static Specification<MovieEntity> nameContains(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<MovieEntity> publishedAfter(LocalDate startDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("publishedDate"), startDate);
    }

    public static Specification<MovieEntity> publishedBefore(LocalDate endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("publishedDate"), endDate);
    }
}
