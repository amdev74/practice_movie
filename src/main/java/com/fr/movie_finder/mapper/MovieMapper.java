package com.fr.movie_finder.mapper;

import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.entity.MovieEntity;
import com.fr.movie_finder.messaging.events.payload.MoviePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ActorMapper.class)
public interface MovieMapper {

    MovieDTO toDTO(MovieEntity entity);

    MovieDTO toDTO(MoviePayload payload);

    MovieEntity toEntity(MovieDTO dto);
}
