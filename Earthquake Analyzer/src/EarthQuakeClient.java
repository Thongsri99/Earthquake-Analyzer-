
import java.util.*;

public class EarthQuakeClient {

	/**
	 * Returns an ArrayList of earthquakes that are above the given minimum magnitude.
	 * 
	 * @param quakeData ArrayList of all earthquakes from a given source
	 * @param magMin Minimum magnitude 
	 * @return
	 */
	public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData, double magMin) {
		ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();

		for (QuakeEntry qe : quakeData) {
			if (qe.getMagnitude() > magMin) {
				answer.add(qe);
			}
		}
		return answer;              
	}


	/**
	 * Filter to select earthquakes that occur within a certain distance from a location
	 * 
	 * @param quakeData ArrayList of all earthquakes from a given source
	 * @param distMax Maximum distance
	 * @param from Location from where to start 
	 * @return
	 */
	public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData, double distMax, Location from) {      
		ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
		// TODO
		for (QuakeEntry qe : quakeData) {
			if (qe.getLocation().distanceTo(from) < distMax) {
				answer.add(qe);
			}
		}
		return answer;
	}

	/**
	 * Returns earthquakes that occur within a certain depth range
	 * 
	 * @param quakeData ArrayList of all earthquakes from a given source
	 * @param minDepth Minimum depth
	 * @param maxDepth Maximum depth
	 * @return
	 */
	public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData , double minDepth , double maxDepth){
		ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();

		for (QuakeEntry qe : quakeData){
			if ((qe.getDepth() < maxDepth) && (qe.getDepth() > minDepth)){
				answer.add(qe);
			}
		}

		return answer;
	}


	/**
	 *  Returns an ArrayList of quakes that have a given phrase
	 * 
	 * @param quakeData rrayList of all earthquakes from a given source
	 * @param where Indicates where in quakeData to look for name
	 * @param name What to look for 
	 * @return
	 */
	public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where , String name){
		ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();

		if (where.equalsIgnoreCase("any")){
			for (QuakeEntry qe : quakeData){
				if ((qe.getInfo().contains(name)))
					answer.add(qe);
			}
		}
		else if (where.equalsIgnoreCase("end")){
			for (QuakeEntry qe : quakeData){
				if ((qe.getInfo().endsWith(name)))
					answer.add(qe);
			}
		}
		else if (where.equalsIgnoreCase("start")){
			for (QuakeEntry qe : quakeData){
				if ((qe.getInfo().startsWith((name))))
					answer.add(qe);
			}
		}           

		return answer;
	}


	/**
	 * 
	 * Prints earthquakes from the list
	 * 
	 * @param list ArrayList of all earthquakes from a given source
	 */
	public void dumpCSV(ArrayList<QuakeEntry> list){
		System.out.println("Latitude,Longitude,Magnitude,Info");
		for(QuakeEntry qe : list){
			System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
					qe.getLocation().getLatitude(),
					qe.getLocation().getLongitude(),
					qe.getMagnitude(),
					qe.getInfo());
		}

	}

	/**
	 * Prints quakes that exceed a given threwhold using the filterByMagnitude method.
	 */
	public void bigQuakes() {
		EarthQuakeParser parser = new EarthQuakeParser();
		//Online Source
		//String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
		
		//Test Source
		String source = "nov20quakedata.atom";
		
		ArrayList<QuakeEntry> list = parser.read(source);
		System.out.println("read data for " + list.size() + " quakes");


		ArrayList<QuakeEntry> listBig = filterByMagnitude(list,5.0);
		for (QuakeEntry qe : listBig) {
			System.out.println(qe); 
		}
	}

	public void createCSV(){
		EarthQuakeParser parser = new EarthQuakeParser();
		//Online Source
		//String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
		
		//Test Source
		String source = "nov20quakedata.atom";
		
		ArrayList<QuakeEntry> list = parser.read(source);
		dumpCSV(list);
		System.out.println("# quakes read: " + list.size());
	}

	public void closeToMe() {
		EarthQuakeParser parser = new EarthQuakeParser();
		
		//Online Source
		//String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
		
		//Test Source
		String source = "nov20quakedata.atom";
		

		ArrayList<QuakeEntry> list = parser.read(source);
		System.out.println("# quakes read: " + list.size());

		//Durham, NC
		//Location city = new Location(35.988, -78.907);
		//Bridgeport, CA
		Location city = new Location(38.17, -118.82);
		ArrayList<QuakeEntry> close = filterByDistanceFrom(list, 1000*1000, city);
		for (int k=0; k< close.size(); k++) {
			QuakeEntry entry = close.get(k);
			double distanceInMeters = city.distanceTo(entry.getLocation());
			System.out.println(distanceInMeters/1000 + " " + entry.getInfo());
		}

	}

	public void quakesofDepth() {
		EarthQuakeParser parser = new EarthQuakeParser();
		//Online Source
		//String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
		
		//Test Source
		String source = "nov20quakedata.atom";
		
		ArrayList<QuakeEntry> list = parser.read(source);
		System.out.println("# quakes read: " + list.size());

		ArrayList<QuakeEntry> ofDepth = filterByDepth(list , -10000.0, -8000.0);
		for (QuakeEntry qe: ofDepth){
			System.out.printf("Location = %s\n" , qe.toString() );
		}

		System.out.printf("Found %d quakes that match that criteria." , ofDepth.size());
	}

	public void quakesByPhrase(){
		EarthQuakeParser parser = new EarthQuakeParser();
		//Online Source
		//String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
		
		//Test Source
		String source = "nov20quakedata.atom";
		
		ArrayList<QuakeEntry> list = parser.read(source);
		System.out.println("# quakes read: " + list.size());

		ArrayList<QuakeEntry> byPhrase = filterByPhrase(list, "any", "Creek");

		for (QuakeEntry qe: byPhrase){
			System.out.println(qe.toString());
		}

		System.out.printf("Found %d quakes that match that criteria." , byPhrase.size());
	}
}
