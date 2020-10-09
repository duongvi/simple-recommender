
/**
 * Write a description of FirstRatings here.
 * 
 * @author Viet Duong 
 * @version October 7, 2020
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    public ArrayList<Movie> loadMovies(String filename){
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Movie> result = new ArrayList<>();
        for (CSVRecord record : parser){
            Movie m = new Movie(record.get("id"), record.get("title"), record.get("year"),
                    record.get("genre"), record.get("director"), record.get("country"),
                    record.get("poster"), Integer.parseInt(record.get("minutes")));
            result.add(m);
        }

        return result;
    }
    
    
    private HashMap<String, Integer> getDirectorsWithMaxNumbers(CSVParser parser){
        // first count the number of movies that each director made
        HashMap<String, Integer> directorNumbers = new HashMap<>();
        int maxMovies = 0;
        for (CSVRecord record : parser){
            // some records can have multiple directors
            String[] directors = record.get("director").split(",|, ");
            for (String s : directors){
                
                if (s.isEmpty()) continue;
                s.trim();
                // count their movies
                if (!directorNumbers.containsKey(s)) {
                    directorNumbers.put(s, 1);
                    maxMovies = 1 > maxMovies ? 1 : maxMovies;
                }
                else {
                    int tmp = directorNumbers.get(s) + 1;
                    // to continuously check what the maximum number of movies is
                    if (tmp > maxMovies) maxMovies = tmp;
                    directorNumbers.put(s, tmp);
                }
            }
        }
        // the directors with maximum number of movies
        HashMap<String, Integer> result = new HashMap<>();
        for (String director : directorNumbers.keySet()){
            if (directorNumbers.get(director) == maxMovies) result.put(director, maxMovies);
        }
        return result;
    }
    
    public void testLoadMovies(){
        String filename = "data/ratedmoviesfull.csv";
        // Testing first method
        ArrayList<Movie> movies = loadMovies(filename);
        System.out.format("Loaded %d movies:\n", movies.size());
        
        // to test other stuff - faster to work with CSV
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        
        HashMap<String, Integer> mostHardworkingDirectors = getDirectorsWithMaxNumbers(parser);
        
        int comedies = 0;
        int longerMovies = 0;
        for (Movie m: movies){
            if (m.getGenres().contains("Comedy")) comedies++;
            if (m.getMinutes() > 150) longerMovies++;
        }

        System.out.format("Number of Comedies: %d\n", comedies);
        System.out.format("Number of movies over 150 minutes long: %d\n", longerMovies);
        System.out.format("Number of directors that produced max number of movies: %d\nThat is: %s"
        , mostHardworkingDirectors.size(), mostHardworkingDirectors);
    }
    
    public ArrayList<Rater> loadRaters (String filename){
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Rater> result = new ArrayList<>();
        for (CSVRecord record : parser){
            String raterID = record.get("rater_id");
            double rating = Double.parseDouble(record.get("rating"));
            String movieID = record.get("movie_id");
            // Check if the rater is already in the list and if he is, get the index
            int alreadyIn = indexOf(result, raterID);
            Rater rater;
            if (alreadyIn < 0) {
                rater = new EfficientRater(raterID);
                rater.addRating(movieID, rating);
                result.add(rater);
            } else {
                rater = result.get(alreadyIn);
                rater.addRating(movieID, rating);
            }
        }
        return result;
    }
    
    private int indexOf (ArrayList<Rater> result, String raterID){
        int i = 0;
        for (Rater r : result){
            if (r.getID().equals(raterID)) return i;
            i++;
        }
        return -1;
    }
    
    
    public void testLoadRaters(){
        String filename = "data/ratings.csv";
        ArrayList<Rater> raters = loadRaters(filename);
        System.out.println("Loaded " + raters.size() + " raters");
        
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        
        String raterID = "193" ;
        String movieID = "1798709" ;
        
        System.out.format("Rater %s has %d ratings\n", raterID, getNumRatingsForRater(raterID, parser));
        
        HashMap <String, Integer> ratersMax = getRatersWithMax(fr.getCSVParser());
        
        System.out.format("%d raters had the maximum number of ratings: %s\n",
            ratersMax.size(),ratersMax);
            
        System.out.format("Movie %s had %d ratings\n", movieID, getNumRatingsForMovie(movieID, fr.getCSVParser()));
        System.out.format("There was %d movies in total\n", getNumMovies(fr.getCSVParser()));
    }
   
    private int getNumMovies(CSVParser parser){
        HashSet<String> movies = new HashSet<>();
        for (CSVRecord record : parser){
            movies.add(record.get("movie_id"));
        }
        return movies.size();
    }
    
    private int getNumRatingsForMovie (String movieID, CSVParser parser){
        int result = 0;
        for(CSVRecord record : parser){
            if (record.get("movie_id").equals(movieID)) result++;
        }
        return result;
    }
    
    private int getNumRatingsForRater (String raterID, CSVParser parser){
        int result = 0;
        for (CSVRecord record : parser){
            if (record.get("rater_id").equals(raterID)) result++;
        }
        return result;
    }
    
    private HashMap<String, Integer> getRatersWithMax (CSVParser parser){
        HashMap<String, Integer> raterNumbers = new HashMap<>();
        int max = 0;
        for (CSVRecord record : parser){
            String raterID = record.get("rater_id");
            if (!raterNumbers.containsKey(raterID)) {
                raterNumbers.put(raterID, 1);
                max = max < 1 ? 1 : max;
            } else {
                int tmp = raterNumbers.get(raterID) + 1;
                //System.out.println(tmp);
                max = max > tmp ? max : tmp;
                raterNumbers.put(raterID, tmp);
            }
        }
        // Same as with directors above - this will return the raters with the most ratings
        HashMap<String, Integer> result = new HashMap<>();
        for (String rater : raterNumbers.keySet()){
            if (raterNumbers.get(rater) == max) result.put(rater, max);
        }
        //System.out.println(max);
        return result;
    }
    
}
