package com.fr.movie_finder.resource;

import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    private MovieService movieService;

    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movie) throws MethodArgumentNotValidException {
        return ResponseEntity.ok().body(movieService.createMovie(movie));
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok().body(movieService.getAllMovies());
    }

    @GetMapping("/{name}")
    public ResponseEntity<MovieDTO> getMovieByName(@PathVariable String name) {
        return ResponseEntity.ok().body(movieService.getMovieByName(name));
    }
}
