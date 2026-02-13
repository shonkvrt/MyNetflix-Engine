package models;

public class Movie {

    private String name;
    private double [] weights;

    public Movie(String name,double [] weights){
        this.name = name;
        this.weights = weights;
    }

    public String getName() {
        return name;
    }

    public double[] getWeights() {
        return weights;
    }

}
