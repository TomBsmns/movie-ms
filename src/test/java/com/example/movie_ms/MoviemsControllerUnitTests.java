package com.example.movie_ms;

import com.example.movie_ms.model.Movie;
import com.example.movie_ms.respository.MovieRespository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MoviemsControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRespository movieRespository;



    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void requestAllMovies_thenReturnJsonMovieList() throws Exception {
        Movie movie = new Movie("Testmovie 1", "Testfilm 1", 2020, 61, 8.1 );
        List<Movie> result = Collections.singletonList(movie);
        given(movieRespository.findAll()).willReturn(result);
        mockMvc.perform(get("/movie/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void requestOneMovie_thenReturnJsonMovie() throws Exception {
        Movie movie = new Movie("Testmovie 1", "Testfilm 1", 2020, 61, 8.1 );
        given(movieRespository.findFirstByUuid(movie.getUuid())).willReturn(movie);
        mockMvc.perform(get("/movie/{uuid}", movie.getUuid()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(movie.getTitle())))
                .andExpect(jsonPath("$.originalTitle", is(movie.getOriginalTitle())))
                .andExpect(jsonPath("$.year", is(movie.getYear())))
                .andExpect(jsonPath("$.runtimeMinutes", is(movie.getRuntimeMinutes())))
                .andExpect(jsonPath("$.rating", is(movie.getRating())))
        ;
    }
}
