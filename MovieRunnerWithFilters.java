
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerWithFilters {
    
    private ThirdRatings load (){
        String filename1 = "ratedmovies_short.csv";
        String filename2 = "ratings_short.csv";
        ThirdRatings tr = new ThirdRatings(filename2);
        //System.out.println("Number of movies: " + sr.getMovieSize());
        System.out.println("Number of raters: " + tr.getRaterSize());
        MovieDatabase.initialize(filename1);
        System.out.println("Size of the database: " + MovieDatabase.size());
        return tr;
    }
    
    public void printAverageRatings(){
        ThirdRatings tr = load();
        ArrayList<Rating> avgRatings = tr.getAverageRatings(1);
        Collections.sort(avgRatings);
        System.out.format("Found %d movies.\n", avgRatings.size());
        for (Rating r : avgRatings){
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()));
        }
    }
    
    public void printAverageRatingsByYear(){
        ThirdRatings tr = load();
        Filter f = new YearAfterFilter(2000);
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(1, f);
        System.out.println(avgRatings.size() + " movies found.");
        Collections.sort(avgRatings);
        for (Rating r : avgRatings){
            System.out.println(r.getValue() + "\t" + MovieDatabase.getYear(r.getItem()) + "\t" +
                        MovieDatabase.getTitle(r.getItem()));
        }
    }
    
    public void printAverageRatingsByGenre(){
        ThirdRatings tr = load();
        Filter f = new GenreFilter("Crime");
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(1, f);
        System.out.println(avgRatings.size() + " movies found.");
        Collections.sort(avgRatings);
        for (Rating r : avgRatings){
            System.out.println(r.getValue() + "\t" + MovieDatabase.getTitle(r.getItem()) + "\n\t" 
                                + MovieDatabase.getGenres(r.getItem()));
        }
    }
    
    public void printAverageRatingsByMinutes(){
        ThirdRatings tr = load();
        Filter f = new MinutesFilter(110, 170);
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(1, f);
        System.out.println(avgRatings.size() + " movies found.");
        Collections.sort(avgRatings);
        for (Rating r : avgRatings){
            System.out.println(r.getValue() + "\t" + MovieDatabase.getMinutes(r.getItem()) + " minutes\t" 
                                    + MovieDatabase.getTitle(r.getItem()));
        }
    }
    
    public void printAverageRatingsByDirectors(){
        ThirdRatings tr = load();
        Filter f = new DirectorsFilter("Charles Chaplin,Michael Mann,Spike Jonze");
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(1, f);
        System.out.println(avgRatings.size() + " movies found.");
        Collections.sort(avgRatings);
        for (Rating r : avgRatings){
            System.out.println(r.getValue() + "\t" + MovieDatabase.getTitle(r.getItem()) + 
                                "\n\t" + MovieDatabase.getDirector(r.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        ThirdRatings tr = load();
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
    
    public void printAverageRatingsByDirectorsAndMinutes(){
        ThirdRatings tr = load();
        AllFilters f = new AllFilters();
        f.addFilter(new DirectorsFilter("Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola"));
        f.addFilter(new MinutesFilter(30, 170));
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(1, f);
        System.out.println(avgRatings.size() + " movies found.");
        Collections.sort(avgRatings);
        for (Rating r : avgRatings){
            System.out.println(r.getValue() + "\tMinutes: " + MovieDatabase.getMinutes(r.getItem()) + "\t" + 
                                MovieDatabase.getTitle(r.getItem()) + "\n\t" + MovieDatabase.getDirector(r.getItem()));
        }
    }
}
