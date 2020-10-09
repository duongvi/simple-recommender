
/**
 * Write a description of class Rater here.
 * 
 * @author Viet Duong 
 * @version October 7, 2020
 */

import java.util.*;

public class EfficientRater implements Rater{
    private String myID;
    private HashMap<String, Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<>();
    }

    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        
        return myRatings.containsKey(item);
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        if (hasRating(item)) return myRatings.get(item).getValue();
        
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        /*
        for(int k=0; k < myRatings.size(); k++){
            list.add(myRatings.get(k).getItem());
        }*/
        for (String key : myRatings.keySet()){
            list.add(myRatings.get(key).getItem());
        }
        
        return list;
    }
    
    public String toString(){
        return "Rater " + getID();
    }
}
