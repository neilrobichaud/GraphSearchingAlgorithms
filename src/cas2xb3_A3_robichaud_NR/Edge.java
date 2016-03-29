package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

public class Edge {
	City v;
	City w;
	double weight;
	String cheapestR;
	
	public Edge(City v, City w, double weight){
		this.v = v;
		this.w = w;
		this.weight = weight;			
	}
	
	/*
	 * a series of methods to weight an edge
	 */
	
	public double weightToV(){
		double distance = getDistanceKm(v.lat,v.lng,w.lat,w.lng);
		double gasVolume=8.2/100 * distance;
		double gasMoney = w.gasPrice * gasVolume;
		double foodMoney=searchRestaurant(v);
		double totalCost = foodMoney + gasMoney;		
		return totalCost;
	}
	public double weightToW(){
		double distance = getDistanceKm(w.lat,w.lng,v.lat,v.lng);
		double gasVolume=8.2/100 * distance;
		double gasMoney = v.gasPrice * gasVolume;
		double foodMoney=searchRestaurant(w);
		double totalCost = foodMoney + gasMoney;		
		return totalCost;
	}
	public double searchRestaurant(City x){
		double foodMoney=0;
		int closestInd = BinarySearch.indexOf(FindPath.rlist,x.lat);				//the index in rlist of the closest restaurant to City v
		ArrayList<Restaurant> closeRestaurantList = BinarySearch.getlist(x.lat, x.lng, closestInd);
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
					cheapestR="mcd";
				}
				else {
					//wendys is smallest
					cheapestR="wendy";
				}
			}
			else{
				if(FindPath.bkMenu.get(FindPath.bkMenu.size()-1) < FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1)){
					//bk smallest
					cheapestR="bk";
				}
				else{
					//wendy smallest
					cheapestR="wendy";
				}
				
			}
		}
		else if (mcd && bk){
			if(FindPath.bkMenu.get(FindPath.bkMenu.size()-1) < FindPath.mcdMenu.get(FindPath.wendyMenu.size()-1)){
				//bk smallest
				cheapestR="bk";
			}
			else{
				//mcd smallest
				cheapestR="mcd";
			}
		}
		else if (bk && wendy){
			if(FindPath.bkMenu.get(FindPath.bkMenu.size()-1) < FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1)){
				//bk smallest
				cheapestR="bk";
			}
			else{
				//wendy smallest
				cheapestR="wendy";
			}
		}
		else if (wendy && mcd){
			if(FindPath.mcdMenu.get(FindPath.bkMenu.size()-1) < FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1)){
				//mcd smallest
				cheapestR="mcd";
			}
			else{
				//wendy smallest
				cheapestR="wendy";
			}
		}
		else if (wendy){
			//wendy smallest
			cheapestR="wendy";
		}
		else if (bk){
			//bk smallest
			cheapestR="bk";
		}
		else if (mcd){
			//mcd smallest
			cheapestR="mcd";
		}
		else{
			//no restaurants
			cheapestR="norestaurant";
		}
		
		if (cheapestR.equals("mcd")){
			foodMoney=FindPath.mcdMenu.get(FindPath.mcdMenu.size()-1);
		}
		else if (cheapestR.equals("bk")){
			foodMoney=FindPath.mcdMenu.get(FindPath.mcdMenu.size()-1);
		}
		else if (cheapestR.equals("wendy")){
			foodMoney=FindPath.mcdMenu.get(FindPath.mcdMenu.size()-1);
		}
		return foodMoney;
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
