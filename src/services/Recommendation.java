package services;

import models.Movie;

import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Recommendation {
    private ArrayList<Movie> movies;
    private double [] weights;

    public Recommendation(ArrayList<Movie> movies, double [] weights){
        this.movies = movies;
        this.weights = weights;
    }

    /** calculate how close the input user weights to a movie and returns result
        between 0-1 (the closer to 1 the similar the movie is) **/
    public double calculateSimilarity(double [] userWeights,Movie movie){
        double result = 0, norm1 = 0, norm2 = 0;
        for (int i = 0; i < weights.length; i++) {
            norm1 += pow(userWeights[i],2 ) * weights[i];
            norm2 += pow(movie.getWeights()[i],2 ) * weights[i];
            result += userWeights[i] * movie.getWeights()[i] * weights[i];
        }
        norm1 = sqrt(norm1);
        norm2 = sqrt(norm2);

        if(norm1 == 0 || norm2 == 0){
            return 0;
        }
        result /= (norm1 * norm2);

        return result;
    }

    /** calculate to each movie how close it is to the user input,
        and takes the three closest ones and return them **/
    public Movie [] getRecommendation(double [] userWeights,String userMovie){
        ArrayList<MovieScore> moviesScore = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            if(movies.get(i).getName().equals(userMovie)){
                continue;
            }
            double result = calculateSimilarity(userWeights,movies.get(i));
            moviesScore.add(new MovieScore(movies.get(i),result));
        }
        moviesScore.sort((a,b) -> Double.compare(b.result,a.result));

        Movie [] suggestedMovies = new Movie[3];
        for (int i = 0; i < suggestedMovies.length; i++) {
            suggestedMovies[i] = moviesScore.get(i).movie;
        }
        return suggestedMovies;
    }

    /** inner class for making a comfortable array of movie scores and their information**/
    private static class MovieScore{
        private Movie movie;
        private double result;

        public MovieScore(Movie movie,double result){
            this.movie = movie;
            this.result = result;
        }

        public Movie getMovie() {
            return movie;
        }

        public double getResult() {
            return result;
        }
    }
}
