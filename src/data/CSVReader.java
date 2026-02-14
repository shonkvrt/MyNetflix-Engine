package data;

import models.Movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CSVReader {
    private String filePath;
    private ArrayList<Movie> movies = new ArrayList<>();
    private String [] genres;
    private double [] generalWeights;

    public CSVReader(String filePath){
        this.filePath = filePath;
    }

    public void readsMovieFromCsvToMovies(){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;

            /** read the movies genres from the csv file to an array **/
            line = br.readLine();
            String [] temp = line.split(",");
            genres = new String[temp.length - 1];
            for (int i = 0; i < genres.length; i++) {
                genres[i] = temp[i + 1];
            }

            /** read the weights to every genre and add put it in an array**/
            line = br.readLine();
            String [] weightsStr = line.split(",");
            generalWeights = addInfoWithoutTitleInDouble(weightsStr);

            /** read all movies from the csv file and creates movie objects with their title
                and weights, and add them to the movies array **/

            int i = 0;
            while ((line = br.readLine()) != null){
                String [] movieInfo = line.split(",");
                String movieName = movieInfo[0];
                double [] movieWeights = addInfoWithoutTitleInDouble(movieInfo);
                movies.add(i,new Movie(movieName,movieWeights));
                i++;
            }
        } catch (IOException IOe) {
            throw new RuntimeException(IOe);
        }




    }

    /** takes the information from the source array, remove the title and returning an array
        with the values in double **/
    public static double[] addInfoWithoutTitleInDouble(String [] source){
        double [] destination = new double [source.length - 1];
        for (int i = 0; i < destination.length; i++) {
            destination[i] = Double.parseDouble(source[i+1]);
        }
        return destination;
    }

    public String getFilePath() {
        return filePath;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public String[] getGenres() {
        return genres;
    }

    public double[] getGeneralWeights() {
        return generalWeights;
    }

    @Override
    public String toString() {
        return "CSVReader{" +
                "filePath='" + filePath + '\'' +
                ", movies=" + movies +
                ", genres=" + Arrays.toString(genres) +
                ", generalWeights=" + Arrays.toString(generalWeights) +
                '}';
    }
}
