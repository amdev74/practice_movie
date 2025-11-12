package com.fr.movie_finder.messaging.events.mapper;

import com.fr.movie_finder.dto.ActorDTO;
import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.messaging.events.MovieReleaseEvent;
import com.fr.movie_finder.messaging.events.payload.ActorPayload;
import com.fr.movie_finder.messaging.events.payload.MoviePayload;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface MovieEventMapper {

    /**
     * Converts MoviePayload (Kafka) to MovieDTO (Service layer).
     *
     * @param payload the Kafka event payload
     * @return the service DTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicationDate", source = "publicationDate")
    MovieDTO toDTO(MoviePayload payload);

    /**
     * Converts ActorPayload (Kafka) to ActorDTO (Service layer).
     *
     * @param payload the Kafka actor payload
     * @return the service actor DTO
     */
    @Mapping(target = "id", ignore = true)
    ActorDTO toDTO(ActorPayload payload);
}