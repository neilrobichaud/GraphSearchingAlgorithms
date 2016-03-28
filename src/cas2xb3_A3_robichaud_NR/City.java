package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

public class City {
	public double gasPrice;
	public double lat;
	public double lng;
	public String name;
	public String state;
	public Boolean visited = false;															// used for dfs instead of marked array
	public Edge edgeTo;
	public int distTo;
	public ArrayList<Edge> adjList;
	 
	public City(double x, double y, String z, String s, String gasPrice){
		this.lat = x;
		this.lng = y;
		this.name = z;
		this.state = s;
		this.gasPrice = Double.parseDouble(gasPrice);
		this.adjList = new ArrayList<Edge>();
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