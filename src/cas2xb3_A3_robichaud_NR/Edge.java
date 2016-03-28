package cas2xb3_A3_robichaud_NR;

public class Edge {
	City v;
	City w;
	double weight;
	
	public Edge(City v, City w, double weight){
		this.v = v;
		this.w = w;
		this.weight = weight;			
	}
	
	public double weightToV(){
		double distance = getDistanceKm(v.lat,v.lng,w.lat,w.lng);
		double gasVolume=8.2/100 * distance;
		double gasMoney = w.gasPrice * gasVolume;
		v.
		return 2;
	}
	
	public double getDistanceKm(double lat1,double lon1,double lat2,double lon2) { //haversine formula from google
		  double R = 6371; 
		  double dLat = toRad(lat2-lat1);  
		  double dLon = toRad(lon2-lon1); 
		  double g = 
		    Math.sin(dLat/2) * Math.sin(dLat/2) +
		    Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * 
		    Math.sin(dLon/2) * Math.sin(dLon/2)
		    ; 
		  double c = 2 * Math.atan2(Math.sqrt(g), Math.sqrt(1-g)); 
		  double d = R * c; 
		  return d;
		}
	public double toRad(double deg){
		return deg * (Math.PI/180);
	}

}
