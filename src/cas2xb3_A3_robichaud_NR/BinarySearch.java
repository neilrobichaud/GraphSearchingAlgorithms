package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

public class BinarySearch {
	
    public static int indexOf(Restaurant[] a, double key) {	//key is input lat
        int lo = 0;
        int mid = -1;
        int hi = a.length - 1;
        while (lo <= hi) {            
            mid = lo + (hi - lo) / 2;
            if      (key < a[mid].lat) hi = mid - 1;			// will return index of closest latitude restaurant
            else if (key > a[mid].lat) lo = mid + 1;
            else return mid;
        }
        return mid;												//returns mid  even if key is not found
    }
    
    public static ArrayList<Restaurant> getlist(double restaurantlat, double restaurantlng, int closestlatindex){
    	ArrayList<Restaurant> closeLatList= new ArrayList<Restaurant>();
    	int i = closestlatindex;
    	while (Math.abs(FindPath.rlist[i].lat - restaurantlat) < 0.5  && FindPath.rlist[i].visited != true){		//adds all restaurant within -0.5 lat points
    		closeLatList.add(FindPath.rlist[i]);
    		i--;
    	}
    	i= closestlatindex;
    	while (Math.abs(FindPath.rlist[i].lat - restaurantlat) < 0.5 && FindPath.rlist[i].visited != true){		//adds all restaurant withing +0.5 lat point
    		closeLatList.add(FindPath.rlist[i]);
    		i++;
    	}    	
    	
    	for (int j = 0; j<closeLatList.size(); j++){
    		if (Math.abs(closeLatList.get(j).lng - restaurantlng)>0.5){		// if the difference between is bigger than 0.5 remove from list
    			closeLatList.remove(j);
    		}    		
    	}
		return closeLatList;
    	
    }
    
}
