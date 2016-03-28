package cas2xb3_A3_robichaud_NR;

public class Restaurant {
	public double lat;
	public double lng;
	public String name;
	public Boolean visited;

	
	public Restaurant(double x, double y, String z){
		this.lat = x;
		this.lng = y;
		this.name = z;
		this.visited=false;
	}
	

	
}
