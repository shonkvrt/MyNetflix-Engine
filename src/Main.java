import data.CSVReader;
import models.Movie;

import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader("C:\\Users\\user\\IdeaProjects\\netflix\\src\\data\\movies.csv");
        csvReader.readsMovieFromCsvToMovies();
        printInfo(csvReader);

    }

    public static void printInfo(CSVReader csvReader)
    {
        System.out.println("file path : " + csvReader.getFilePath());

        System.out.println("genres : ");
        System.out.println(Arrays.toString(csvReader.getGenres()));

        System.out.println("general weights : ");
        System.out.println(Arrays.toString(csvReader.getGeneralWeights()));

        System.out.println("movies : ");
        for (int i = 0; i < csvReader.getMovies().size(); i++) {
            Movie movie = csvReader.getMovies().get(i);
            System.out.println(movie.getName());
            System.out.println(Arrays.toString(movie.getWeights()));
        }
    }
}