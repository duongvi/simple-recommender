
/**
 * Write a description of SecondRatings here.
 * 
 * @author Viet Duong
 * @version October 7, 2020
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings(String filename1, String filename2){
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(filename1);
        myRaters = fr.loadRaters(filename2);
    }
    
    public SecondRatings() {
        // default constructor
        this("data/ratedmoviesfull.csv", "data/ratings.csv");
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
        for (Movie m : myMovies){
            String movieID = m.getID();
            double avg = getAverageByID(movieID, minimalRaters);
            if (avg != 0.0) avgRatings.add(new Rating(movieID, avg));
        }
        return avgRatings;
    }
    
    public String getTitle(String id){
        for (Movie m : myMovies){
            if (m.getID().equals(id)) return m.getTitle();
        }
        
        return "Movie with id " + id + " not found.";
    }
    
    public String getID(String title){
        for (Movie m: myMovies){
            if(m.getTitle().equals(title)) return m.getID();
        }
        return "NO SUCH TITLE";
    }
    
    public int getMovieSize(){
        return myMovies.size();
    }
    
    public int getRaterSize(){
        return myRaters.size();
    }
}
