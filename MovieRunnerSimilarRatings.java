
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author Viet Duong 
 * @version October 8, 2020
 */

import java.util.*;

public class MovieRunnerSimilarRatings {
    private FourthRatings load (){
        String filename1 = "ratedmoviesfull.csv";
        String filename2 = "ratings.csv";
        FourthRatings obj = new FourthRatings();
        //System.out.println("Number of movies: " + sr.getMovieSize());
        //System.out.println("Number of raters: " + tr.getRaterSize());
        MovieDatabase.initialize(filename1);
        RaterDatabase.initialize(filename2);
        System.out.println("Size of the movie database: " + MovieDatabase.size());
        System.out.println("Size of the rater database: " + RaterDatabase.size());
        return obj;
    }

    public void printAverageRatings(){
        FourthRatings tr = load();
        ArrayList<Rating> avgRatings = tr.getAverageRatings(1);
        Collections.sort(avgRatings);
        System.out.format("Found %d movies.\n", avgRatings.size());
        for (Rating r : avgRatings){
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()));
        }
    }

    public void printAverageRatingsByYearAfterAndGenre(){
        FourthRatings tr = load();
        AllFilters f = new AllFilters();
        Filter yearFilter = new YearAfterFilter(1980);
        f.addFilter(yearFilter);
        f.addFilter(new GenreFilter("Romance"));
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(1, f);
        System.out.println(avgRatings.size() + " movies found.");
        Collections.sort(avgRatings);
        for (Rating r : avgRatings){
            System.out.println(r.getValue() + "\t" + MovieDatabase.getYear(r.getItem()) + "\t" + 
                "\n\t" + MovieDatabase.getGenres(r.getItem()));
        }
    }

    public void printSimilarRatings(){
        FourthRatings tr = load();
        ArrayList<Rating> similarRatings = tr.getSimilarRatings("65", 20, 5);
        System.out.println("Found " + similarRatings.size() + " movies");
        for (Rating r : similarRatings){
            System.out.println(r.getValue() + "\t" + MovieDatabase.getTitle(r.getItem()));
        }
    }

    public void printSimilarRatingsByGenre(){
        FourthRatings tr = load();
        ArrayList<Rating> similarRatings = tr.getSimilarRatingsByFilter("65", 20, 5, new GenreFilter("Action"));
        System.out.println("Found " + similarRatings.size() + " movies");
        for (Rating r : similarRatings){
            System.out.println(r.getValue() + "\t" + MovieDatabase.getTitle(r.getItem()));
            System.out.println("\t" + MovieDatabase.getGenres(r.getItem()));
        }
    }

    public void printSimilarRatingsByDirector (){
        FourthRatings tr = load();
        ArrayList<Rating> similarRatings = tr.getSimilarRatingsByFilter("1034", 10, 3
            , new DirectorsFilter("Clint Eastwood,Sydney Pollack,David Cronenberg,Oliver Stone"));
        System.out.println("Found " + similarRatings.size() + " movies");
        for (Rating r : similarRatings){
            System.out.println(r.getValue() + "\t" + MovieDatabase.getTitle(r.getItem()));
            System.out.println("\t" + MovieDatabase.getDirector(r.getItem()));
        }
    }

    public void printSimilarRatingsByGenreAndMinutes(){
        FourthRatings tr = load();
        AllFilters f = new AllFilters();
        f.addFilter(new GenreFilter("Adventure"));
        f.addFilter(new MinutesFilter(100, 200));
        ArrayList<Rating> similarRatings = tr.getSimilarRatingsByFilter("65", 20, 10, f);
        System.out.println("Found " + similarRatings.size() + " movies");
        for (Rating r : similarRatings){
            System.out.println(r.getValue() + "\t" + MovieDatabase.getTitle(r.getItem()) + "\tDuration: "
                                        + MovieDatabase.getMinutes(r.getItem()));
            System.out.println("\t" + MovieDatabase.getGenres(r.getItem()));
        }
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes(){
        FourthRatings tr = load();
                AllFilters f = new AllFilters();
        f.addFilter(new YearAfterFilter(2000));
        f.addFilter(new MinutesFilter(80, 100));
        ArrayList<Rating> similarRatings = tr.getSimilarRatingsByFilter("65", 10, 5, f);
        System.out.println("Found " + similarRatings.size() + " movies");
        for (Rating r : similarRatings){
            String movieID = r.getItem();
            System.out.println(MovieDatabase.getTitle(movieID) + "\t" + MovieDatabase.getYear(movieID)
                                + "\t" + MovieDatabase.getMinutes(movieID) + "\t" + r.getValue());
        }
    }
}