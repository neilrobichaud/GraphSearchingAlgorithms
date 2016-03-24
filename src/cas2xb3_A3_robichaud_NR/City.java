package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

public class City {
	public double lat;
	public double lng;
	public String name;
	public String[] states;
	public ArrayList<City> adjList;
	 
	public City(double x, double y, String z, String[] s){
		this.lat = x;
		this.lng = y;
		this.name = z;
		this.states = s;
		this.adjList = new ArrayList<City>();
	}
	
	public ArrayList<Restaurant> nearbyRestaurants(){
		int closestlatindex = BinarySearch.indexOf(FindPath.rlist, lat);
		return BinarySearch.getlist(lat, lng, closestlatindex);		
	}
	
	
	

}
//TODO import the restaurant csvs and sort them
// 
// create graph each city is a node
// edges are weighted by 