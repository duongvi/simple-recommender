
/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DirectorsFilter implements Filter{
    String directors;
    public DirectorsFilter(String directors){
        this.directors = directors;
    }
    
    
    public boolean satisfies (String id){
        String[] directorsFromDB = MovieDatabase.getDirector(id).split(",|, ");
        for (String director : directorsFromDB){
            if (directors.contains(director)) return true;
        }
        return false;
    }
}
