package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

public class Edge {
	City v;
	City w;

	String cheapestR;
	
	public Edge(City v, City w){
		this.v = v;
		this.w = w;
		
	}
	
	/*
	 * a series of methods to weight an edge
	 */
	
	public double weightToV(){
		double distance = getDistanceKm(v.lat,v.lng,w.lat,w.lng);
		double gasVolume=8.2/100 * distance;
		double gasMoney = w.gasPrice/100 * gasVolume;
		double foodMoney=searchRestaurant(v);
		double totalCost = foodMoney + gasMoney;		
		return totalCost;
	}
	public double weightToW(){
		double distance = getDistanceKm(w.lat,w.lng,v.lat,v.lng);
		double gasVolume=8.2/100 * distance;
		double gasMoney = v.gasPrice/100 * gasVolume;
		double foodMoney=searchRestaurant(w);
		//System.out.println(w.name + "--> " + v.name + " : " + gasMoney + " " + foodMoney);
		double totalCost = foodMoney + gasMoney;		
		return totalCost;
	}
	/*
	 * the hard way to find the cheapest restaurant at any given city
	 */
	public double searchRestaurant(City x){
		
		double foodMoney=0;
		int closestInd = BinarySearch.indexOf(FindPath.rlist,x.lat);				//the index in rlist of the closest restaurant to City v
		//System.out.print(closestInd + " ");
		ArrayList<Restaurant> closeRestaurantList = BinarySearch.getlist(x.lat, x.lng, closestInd);
		//System.out.print(closeRestaurantList.get(0).name);
		Boolean wendy = true;
		Boolean mcd = true;
		Boolean bk = true;
		//System.out.print(closeRestaurantList.size() + " ");
		for (int i=0; i< closeRestaurantList.size();i++){
			//System.out.print(closeRestaurantList.get(i).name);
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
		if(mcd && bk && wendy && FindPath.mcdMenu.size()>0){
			if(FindPath.mcdMenu.get(FindPath.mcdMenu.size()-1) < FindPath.bkMenu.get(FindPath.bkMenu.size()-1)){
				if (FindPath.mcdMenu.get(FindPath.mcdMenu.size()-1) < FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1)){
					//mcd is smallest
					cheapestR="mcd";
					foodMoney=FindPath.mcdMenu.get(FindPath.mcdMenu.size()-1);
					FindPath.mcdMenu.remove(FindPath.mcdMenu.size()-1);
				}
				else {
					//wendys is smallest
					cheapestR="wendy";
					foodMoney=FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1);
					FindPath.wendyMenu.remove(FindPath.wendyMenu.size()-1);
				}
			}
			else{
				if(FindPath.bkMenu.get(FindPath.bkMenu.size()-1) < FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1)){
					//bk smallest
					cheapestR="bk";
					foodMoney=FindPath.bkMenu.get(FindPath.bkMenu.size()-1);
					foodMoney=FindPath.bkMenu.remove(FindPath.bkMenu.size()-1);
				}
				else{
					//wendy smallest
					cheapestR="wendy";
					foodMoney=FindPath.wendyMenu.get(FindPath.wendyMenu.size()-1);
					foodMoney=FindPath.wendyMenu.remove(FindPath.wendyMenu.size()-1);
				}
				
			}
		}		
		else{foodMoney=5;}
 		
		//System.out.print("FoodMoney:" + foodMoney + "  ");
		
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
