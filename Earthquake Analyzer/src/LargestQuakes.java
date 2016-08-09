import java.util.*;

/**
 * Finds largest Earthquakes
 * 
 * @author Pete Thongsri
 * @version 1.0.1 08/08/2016
 */
public class LargestQuakes {

    /**
     * Uses getLargest to find the n largest quakes
     */
    public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //Online Source
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        
        //Test Source
        //String source = "data/nov20quakedatasmall.atom";
        
        
        //Larger Test Source
        String source = "data/nov20quakedata.atom";
        
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("# quakes read: " + list.size());
        
        ArrayList<QuakeEntry> largest = getLargest(list, 5);
        
        for (QuakeEntry qe : largest){
            System.out.println(qe.toString());
        }
        
    }
    
    
    /**
     * Returns the index of the largest quake from a source
     * 
     * @param quakeData ArrayList of the all the quakes from a given source
     * @return
     */
    public int indexOfLargest(ArrayList<QuakeEntry> quakeData){
        int index  = 0;
        
        for (int i = 1; i < quakeData.size() ; i++){
            if (quakeData.get(i).getMagnitude() > quakeData.get(index).getMagnitude()){
                index = i;
            }
        }
        return index;
    }
    
    /**
     * Returns the n largest quakes
     * 
     * @param quakeData ArrayList of the all the quakes from a given source
     * @param howMany Specifies how many quakes to return
     * @return
     */
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData , int howMany){
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (int i = 0 ; i < howMany ; i++){
            answer.add(copy.get(indexOfLargest(copy)));
            copy.remove(copy.get(indexOfLargest(copy)));
        }
        
        return answer;
    }
}
