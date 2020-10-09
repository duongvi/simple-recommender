
/**
 * Write a description of SecondRatings here.
 * 
 * @author Viet Duong
 * @version October 7, 2020
 */

import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings(String ratingFile){
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters("data/" + ratingFile);
    }
    
    public ThirdRatings() {
        // default constructor
        this("data/ratings.csv");
    }
    
    private double getAverageByID (String id, int minimalRaters){
        double sum = 0;
        int numberOfRaters = 0;
        for(Rater r : myRaters){
            double rating = r.getRating(id);
            if (rating > -1) {
                sum += rating;
                numberOfRaters++;
            }
        }
        if (numberOfRaters >= minimalRaters) return sum/numberOfRaters;
        return 0.0;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> avgRatings = new ArrayList<>();
        ArrayList<String> myMovies = MovieDatabase.filterBy(new TrueFilter());
        for (String movieID : myMovies){
            double avg = getAverageByID(movieID, minimalRaters);
            if (avg != 0.0) avgRatings.add(new Rating(movieID, avg));
        }
        return avgRatings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter f){
        ArrayList<String> movies = MovieDatabase.filterBy(f);
        ArrayList<Rating> avgRatings = new ArrayList<>();
        for (String movieID : movies){
            double avg = getAverageByID(movieID, minimalRaters);
            if (avg != 0.0) avgRatings.add(new Rating(movieID, avg));
        }
        return avgRatings;
    }
    public int getRaterSize(){
        return myRaters.size();
    }
}
