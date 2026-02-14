import data.CSVReader;
import models.Movie;
import services.RecommendationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader("C:\\Users\\user\\IdeaProjects\\netflix\\src\\data\\movies.csv");
        csvReader.readsMovieFromCsvToMovies();
        printMoviesStats(csvReader.getMovies());
        Movie [] suggestedMovies = chooseOption(csvReader,new Scanner(System.in));
        printMoviesNames(new ArrayList<>(Arrays.asList(suggestedMovies)));



    }

    public static void printMoviesStats(ArrayList<Movie> movies)
    {
        for (int i = 1; i <= movies.size(); i++) {
            Movie movie = movies.get(i - 1);
            System.out.println(i + ". " + movie.getName() + " : " + Arrays.toString(movie.getWeights()));
        }
        System.out.println();
    }

    public static void printMoviesNames(ArrayList<Movie> movies){
        for (int i = 1; i <= movies.size(); i++) {
            Movie movie = movies.get(i - 1);
            System.out.println(i + ". " + movie.getName());
        }
        System.out.println();
    }

    public static Movie [] chooseOption(CSVReader csvReader, Scanner scanner) {
        String userMovie = null;
        double [] userWeights = new double[csvReader.getGenres().length];
        Movie [] suggestedMovies = new Movie[3];
        int choice;
        RecommendationService recommendationService =
                new RecommendationService(csvReader.getMovies(),csvReader.getGeneralWeights());
        do {
            System.out.println("1. Recommendation by liked movie");
            System.out.println("2. Recommendation by genre preferences");
            System.out.println("3. Exit");
            System.out.println();
            System.out.print("Choose an option : ");
            choice = scanner.nextInt();
            System.out.println();

            switch (choice){
                case 1:
                    Movie chosenMovie = choiceOne(csvReader,scanner);
                    userMovie = chosenMovie.getName();
                    userWeights = chosenMovie.getWeights();
                    break;

                case 2:
                    userMovie = null;
                    userWeights = choiceTwo(csvReader,scanner);
                    break;

                case 3:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Wrong input, try again!");
                    System.out.println();
            }
            suggestedMovies = recommendationService.getRecommendation(userMovie,userWeights);
            return  suggestedMovies;

        } while (true);



    }

    public static Movie choiceOne(CSVReader csvReader, Scanner scanner){
        int indexMovie = 0;
        do{
            if(indexMovie < 0 || indexMovie > 29){
                System.out.println("Wrong input, try again!");
                System.out.println();
            }
            System.out.print("Enter a movie (1-30) : ");
            indexMovie = scanner.nextInt() - 1;
            System.out.println();
        }while(indexMovie < 0 || indexMovie > 29);

        return csvReader.getMovies().get(indexMovie);

    }

    public static double [] choiceTwo(CSVReader csvReader, Scanner scanner){
        double [] userWeights = new double[csvReader.getGenres().length];
        for (int i = 0; i < csvReader.getGenres().length; i++) {
            do{
                System.out.print("Enter " + csvReader.getGenres()[i] + "(between 0.0 - 1.0): ");
                userWeights[i] = scanner.nextDouble();
            }while(userWeights[i] < 0.0 || userWeights[i] > 1.0);
        }
        System.out.println();

        return userWeights;
    }
}