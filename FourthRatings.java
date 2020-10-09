
/**
 * Write a description of FourthRatings here.
 * 
 * @author Viet Duong 
 * @version October 8, 2020
 */

import java.util.*;

public class FourthRatings {

    
    private double getAverageByID (String id, int minimalRaters){
        double sum = 0;
        int numberOfRaters = 0;
        for(Rater r : RaterDatabase.getRaters()){
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
    
      private double dotProduct(Rater me, Rater r){
        double sum = 0;
        for (String myRating : me.getItemsRated()){
            if (r.hasRating(myRating)) {
                sum += (me.getRating(myRating) - 5.0) * (r.getRating(myRating) - 5.0);
            }                                  
        }
        
        return sum;
    }
    
    private ArrayList<Rating> getSimilarities (String id){
        ArrayList<Rating> similarities = new ArrayList<>();
        Rater me = RaterDatabase.getRater(id);
        for (Rater r : RaterDatabase.getRaters()){
            if (r.getID().equals(id)) continue;
            double dotProduct = dotProduct(me, r);
            // Add a Rating in the form of (Rater ID, his similarity measure) to the result
            // Only add if it is positive
            if (dotProduct > 0) similarities.add(new Rating(r.getID(),dotProduct));
        }
        // Sort the raters so that the most similar ones to id are at the top
        Collections.sort(similarities, Collections.reverseOrder());
        System.out.println(similarities);
        return similarities;
    }
    
    public ArrayList<Rating> getSimilarRatings (String id, int numSimilarRaters, int minimalRaters){
        return getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new TrueFilter());
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter f){
        ArrayList<Rating> result = new ArrayList<>();
        // Get the similarities of all the raters to me (id) 
        ArrayList<Rating> similarRatersFull = getSimilarities(id);
        // TODO : Subject to change - but basically only get the top numSimilarRaters from this list
        ArrayList<Rating> similarRaters = new ArrayList<Rating>();
        for (int i = 0; i < numSimilarRaters; i++){
            similarRaters.add(similarRatersFull.get(i));
        }
        // Retrieve movies from the database
        ArrayList<String> myMovies = MovieDatabase.filterBy(f);
        // For all movies in the database, get weighted averages
        for (String movieID : myMovies){
            double weightedAverage = getWeightedAverage(movieID, similarRaters, minimalRaters);
            // If at least minimalRaters (of the top numSimilarRaters) have rated the movie 
            if (weightedAverage > -1) {
                result.add(new Rating(movieID, weightedAverage));
            }
        }
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }
    
    private double getWeightedAverage (String movieID, ArrayList<Rating> similarRaters, int minimalRaters){
        double sum = 0;
        double cnt = 0;
        // get a weighted average of the movie with movieID, only if at least minimalRaters have rated it
        for (Rating r : similarRaters){
            Rater rater = RaterDatabase.getRater(r.getItem());
            if (rater.hasRating(movieID)) {
                cnt++;
                sum += rater.getRating(movieID) * r.getValue();
            }
        }
        
        // the condition that at least minimalRaters have rated
        if (cnt >= minimalRaters) return sum/cnt;
        return -1;
    }

}
