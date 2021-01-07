package com.example.movie_ms.controller;

import com.example.movie_ms.model.Movie;
import com.example.movie_ms.respository.MovieRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MovieController {
    @Autowired
    private MovieRespository movieRespository;

    @GetMapping("/movie/all")
    public List<Movie> getAll(){
        return movieRespository.findAll();
    }

    @GetMapping("/movie/{uuid}")
    public Movie getOne(@PathVariable UUID uuid){
        return movieRespository.findFirstByUuid(uuid.toString());
    }
}