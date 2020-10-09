
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author Viet Duong 
 * @version October 7, 2020

 */

import java.util.*;
import edu.duke.*;

public class MovieRunnerAverage {
    public void printAverageRatings(){
        String filename1 = "data/ratedmoviesfull.csv";
        String filename2 = "data/ratings.csv";
        SecondRatings sr = new SecondRatings(filename1, filename2);
        System.out.println("Number of movies: " + sr.getMovieSize());
        System.out.println("Number of raters: " + sr.getRaterSize());
        
        ArrayList<Rating> avgRatings = sr.getAverageRatings(3);
        Collections.sort(avgRatings);
        for (Rating r : avgRatings){
            System.out.println(r.getValue() + " " + sr.getTitle(r.getItem()));
        }
    }
    
    public void getAverageRatingOneMovie(){
        String filename1 = "data/ratedmovies_short.csv";
        String filename2 = "data/ratings_short.csv";
        SecondRatings sr = new SecondRatings(filename1, filename2);
        String movie = "The Godfather";
        String id = sr.getID(movie);
        ArrayList<Rating> avgRatings = sr.getAverageRatings(3);
        for (Rating r : avgRatings){
            if (r.getItem().equals(id)) {
                System.out.println(movie + " : " + r.getValue());
            }
        }
    }
}
