package com.example.movie_ms;

import com.example.movie_ms.model.Movie;
import com.example.movie_ms.respository.MovieRespository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRespository movieRespository;

    int initalNumberMovies = 0;
    Movie movie1 = new Movie("Testmovie 1", "Testfilm 1", 2020, 61, 8.1 );
    Movie movie2 = new Movie("Testmovie 2", "Testfilm 2", 2020, 62, 8.2 );
    Movie movie3 = new Movie("Testmovie 3", "Testfilm 3", 2020, 63, 8.3 );

    @BeforeEach
    public void beforeAllTests() {
        initalNumberMovies = movieRespository.findAll().size();
        movieRespository.save(movie1);
        movieRespository.save(movie2);
        movieRespository.save(movie3);
    }

    @AfterEach
    public void afterAllTests() {
        movieRespository.deleteById(movie1.getId());
        movieRespository.deleteById(movie2.getId());
        movieRespository.deleteById(movie3.getId());
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void requestAllMovies_thenReturnJsonMovieList() throws Exception {
        mockMvc.perform(get("/movie/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(initalNumberMovies + 3)));
    }

    @Test
    public void requestOneMovie_thenReturnJsonMovie() throws Exception {
        mockMvc.perform(get("/movie/{uuid}", movie1.getUuid()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(movie1.getTitle())))
                .andExpect(jsonPath("$.originalTitle", is(movie1.getOriginalTitle())))
                .andExpect(jsonPath("$.year", is(movie1.getYear())))
                .andExpect(jsonPath("$.runtimeMinutes", is(movie1.getRuntimeMinutes())))
                .andExpect(jsonPath("$.rating", is(movie1.getRating())))
                ;
    }

}
