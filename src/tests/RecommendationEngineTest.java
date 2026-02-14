package tests;

import models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.RecommendationService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecommendationEngineTest {

    private RecommendationService service;
    private ArrayList<Movie> movies;
    private double[] weights;

    @BeforeEach
    void setUp(){
        movies = new ArrayList<>();
        weights = new double[]{0.1,0.2,0.3};
        service = new RecommendationService(movies,weights);
    }

    @Test
    void testLength(){
        double [] weights1 = {0.1,0.2,0.3};
        double [] weights2 = {0.1,0.2};

        assertThrows(IllegalArgumentException.class,
                ()-> service.calculateSimilarity(weights1,weights2));
    }

    @Test
    void testSame(){
        double [] weights1 = {0.1,0.2,0.3};
        double [] weights2 = {0.1,0.2,0.3};

        double result = service.calculateSimilarity(weights1,weights2);
        assertEquals(1.0,result);
    }

    @Test
    void testNotSame(){
        double [] weights1 = {0.0,1.0,1.0};
        double [] weights2 = {1.0,0.0,0.0};

        double result = service.calculateSimilarity(weights1,weights2);
        assertEquals(0.0,result);
    }

    @Test
    void testWeightedSimilarity(){
        double [] weights1 = {1.0,1.0};
        double [] weights2 = {1.0,0.0};
        double [] w = {0.5,0.5};

        RecommendationService weightedService = new RecommendationService(movies,w);
        double result = weightedService.calculateSimilarity(weights1,weights2);
        assertEquals(0.7071,result,0.01);
    }


}
