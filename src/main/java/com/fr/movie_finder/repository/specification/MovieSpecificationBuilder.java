package com.fr.movie_finder.repository.specification;

import com.fr.movie_finder.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class MovieSpecificationBuilder {

    private Specification<MovieEntity> specification;

    public MovieSpecificationBuilder() {
        this.specification = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public MovieSpecificationBuilder withName(String name) {
        if (name != null && !name.isBlank()) {
            specification = specification.and(MovieSpecifications.nameContains(name));
        }
        return this;
    }

    public MovieSpecificationBuilder publishedAfter(LocalDate startDate) {
        if (startDate != null) {
            specification = specification.and(MovieSpecifications.publishedAfter(startDate));
        }
        return this;
    }

    public MovieSpecificationBuilder publishedBefore(LocalDate endDate) {
        if (endDate != null) {
            specification = specification.and(MovieSpecifications.publishedBefore(endDate));
        }
        return this;
    }

    public Specification<MovieEntity> build() {
        return specification;
    }
}
