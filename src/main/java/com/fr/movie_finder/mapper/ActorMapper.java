package com.fr.movie_finder.mapper;

import com.fr.movie_finder.dto.ActorDTO;
import com.fr.movie_finder.entity.ActorEntity;
import com.fr.movie_finder.entity.ActorMovieEntity;
import com.fr.movie_finder.messaging.events.payload.ActorPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface ActorMapper {

    ActorDTO toDTO(ActorEntity entity);

    ActorDTO toDTO(ActorPayload payload);

    ActorEntity toEntity(ActorDTO dto);

    @Mapping(source = "actor.id", target = "id")
    @Mapping(source = "actor.firstname", target = "firstname")
    @Mapping(source = "actor.lastname", target = "lastname")
    ActorDTO toDTO(ActorMovieEntity actorMovieEntity);
}
