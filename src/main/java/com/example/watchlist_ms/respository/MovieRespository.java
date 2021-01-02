package com.example.watchlist_ms.respository;

import com.example.watchlist_ms.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MovieRespository extends JpaRepository<Movie, Integer> {
    Movie findFirstByUuid(String uuid);
}
