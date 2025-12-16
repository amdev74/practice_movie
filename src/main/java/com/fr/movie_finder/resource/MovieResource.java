package com.fr.movie_finder.resource;

import com.fr.movie_finder.dto.MovieDTO;
import com.fr.movie_finder.service.MovieService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    private final MovieService movieService;

    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movie) throws MethodArgumentNotValidException {
        return ResponseEntity.ok().body(movieService.createMovie(movie));
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> searchMovies(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<MovieDTO> movies;

        if (startDate != null) {
            if (endDate == null) {
                endDate = LocalDate.now();
            }

            movies = movieService.getMoviesByStartDateAndEndDate(startDate, endDate);
        } else {
            movies = movieService.getAllMovies();
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{name}")
    public ResponseEntity<MovieDTO> getMovieByName(@PathVariable String name) {
        return ResponseEntity.ok().body(movieService.getMovieByName(name));
    }
}
