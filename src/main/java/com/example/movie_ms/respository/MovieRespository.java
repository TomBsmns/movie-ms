package com.example.movie_ms.respository;

import com.example.movie_ms.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRespository extends JpaRepository<Movie, Integer> {
    Movie findFirstByUuid(String uuid);
}
