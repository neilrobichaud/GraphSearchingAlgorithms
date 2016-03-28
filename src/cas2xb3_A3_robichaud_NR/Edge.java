package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

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
		int closestInd = BinarySearch.indexOf(FindPath.rlist,v.lat);				//the index in rlist of the closest restaurant to City v
		ArrayList<Restaurant> closeRestaurantList = BinarySearch.getlist(v.lat, v.lng, closestInd);
		Boolean wendy = false;
		Boolean mcd = false;
		Boolean bk = false;
		for (int i=0; i< closeRestaurantList.size();i++){
			if (closeRestaurantList.get(i).name.contains("Wendy's")){
				wendy=true;
			}
			else if(closeRestaurantList.get(i).name.contains("BurgerKing")){
				bk=true;
			}
			else if(closeRestaurantList.get(i).name.contains("McDonalds")){
				mcd=true;
			}
		}
		if(mcd && bk && wendy){
			if(FindPath.mcdMenu.get(FindPath.mcdMenu.size()-1) < FindPath.mcdMenu.get(FindPath.mcdMenu.size()-1)){
				if (FindPath.mcdMenu.get(FindPath.mcdMenu.size()-1) < FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1)){
					//mcd is smallest
				}
				else {
					//wendys is smallest
				}
			}
			else{
				if(FindPath.bkMenu.get(FindPath.bkMenu.size()-1) < FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1)){
					//bk smallest
				}
				else{
					//wendy smallest
				}
				
			}
		}
		else if (mcd && bk){
			if(FindPath.bkMenu.get(FindPath.bkMenu.size()-1) < FindPath.mcdMenu.get(FindPath.wendyMenu.size()-1)){
				//bk smallest
			}
			else{
				//mcd smallest
			}
		}
		else if (bk && wendy){
			if(FindPath.bkMenu.get(FindPath.bkMenu.size()-1) < FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1)){
				//bk smallest
			}
			else{
				//wendy smallest
			}
		}
		else if (wendy && mcd){
			if(FindPath.mcdMenu.get(FindPath.bkMenu.size()-1) < FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1)){
				//mcd smallest
			}
			else{
				//wendy smallest
			}
		}
		else if (wendy){
			//wendy smallest
		}
		else if (bk){
			//bk smallest
		}
		else if (mcd){
			//mcd smallest
		}
		else{
			
		}
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
