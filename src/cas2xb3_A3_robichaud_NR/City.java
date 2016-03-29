package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

public class City {
	public double gasPrice;
	public double lat;
	public double lng;
	public String name;
	public String state;
	public Boolean visited = false;											//has this city been visited in a search?
	public Edge edgeTo;														//what was the last city on this path
	public int distTo;														//used for bfs
	public ArrayList<Edge> adjList;											//adjacency list
	 
	public City(double x, double y, String z, String s, String gasPrice){
		this.lat = x;
		this.lng = y;
		this.name = z;
		this.state = s;
		this.gasPrice = Double.parseDouble(gasPrice);
		this.adjList = new ArrayList<Edge>();
	}
	
/*
 * use binary search to find the closest restaurants to this city
 */
	public ArrayList<Restaurant> nearbyRestaurants(){
		int closestlatindex = BinarySearch.indexOf(FindPath.rlist, lat);
		return BinarySearch.getlist(lat, lng, closestlatindex);		
	}
	
	
	

}
