package cas2xb3_A3_robichaud_NR;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FindPath {
	public static ArrayList<String> MDmeals;	//sorted arraylist of meals in decreasing order of price
	public static ArrayList<String> BKmeals;
	public static ArrayList<String> Wmeals;
	public static Restaurant[] rlist;
	public static ArrayList<City> citylist;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Restaurant> bklist = readFromFile("2XB3_A3_DataSets/burgerking.csv");
		ArrayList<Restaurant> mcdlist = readFromFile("2XB3_A3_DataSets/mcdonalds.csv");
		ArrayList<Restaurant> wendyslist = readFromFile("2XB3_A3_DataSets/wendys.csv");
		
		ArrayList<Restaurant> allRList = new ArrayList<Restaurant>();
		allRList.addAll(bklist);
		allRList.addAll(mcdlist);
		allRList.addAll(wendyslist);
		rlist = allRList.toArray(new Restaurant[allRList.size()]);
		citylist = new ArrayList<City>();
		citylist = readCityFromFile("2XB3_A3_DataSets/zips1990.csv");
		
		readEdgeFromFile("2XB3_A3_DataSets/connectedCities.txt");
		//addEdge(cityfinder("Boston"),cityfinder("el paso"),3.0);
		int count=0;
		for(int i=0;i<citylist.size();i++){
			if (!citylist.get(i).adjList.isEmpty()){
				for (int j=0;j<citylist.get(i).adjList.size();j++){
					System.out.println(citylist.get(i).adjList.get(j).v.name+" -->"+citylist.get(i).adjList.get(j).w.name);
					count++;
				}				
				
			}
		}
		System.out.print(count);
		//System.out.print(cityfinder("Boston").adjList.get(0).weight);
		
	}
	
	public static void addEdge(City x, City y, double weight){
		Edge e = new Edge(x, y, weight);
		x.adjList.add(e);
		y.adjList.add(e);
	}
	
	public static ArrayList<Restaurant> readFromFile(String filename) {		//method to read from the input file, returns each line as an string in an arraylist
		Scanner input;
		ArrayList<Restaurant> current = new ArrayList<Restaurant>();
		try {
			input = new Scanner(new File(filename));
			while (input.hasNextLine()) {
				String k = input.nextLine();
				String[] l= k.split(",");
				Restaurant a = new Restaurant(Double.parseDouble(l[0]),Double.parseDouble(l[1]),l[2]);
				current.add(a);
			}

			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return current;//.toArray(new Restaurant[current.size()]);											//returns the arraylist
	}
	public static ArrayList<City> readCityFromFile(String filename){
		Scanner input;
		ArrayList<City> current = new ArrayList<City>();
		try {
			input = new Scanner(new File(filename));
			input.nextLine();																				//skip first row of headers
			while (input.hasNextLine()) {
				String k = input.nextLine();
				String[] l= k.split(",");
				String[] state = {l[2]};
				if (cityfinder(l[3])==null){	// if city is not already in citylist
				City a = new City(Double.parseDouble(l[4]), Double.parseDouble(l[5]), l[3], state);
				current.add(a);
				}
			}

			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return current;//.toArray(new Restaurant[current.size()]);											//returns the arraylist
	}
	public static void readEdgeFromFile(String filename) {		//method to read from the input file, returns each line as an string in an arraylist
		Scanner input;
		int open;
		int closed;
		Boolean first;
		ArrayList<City> current = new ArrayList<City>();
		try {
			input = new Scanner(new File(filename));
			
			while (input.hasNextLine()) {
				String k = input.nextLine();
				first=true;
				open=0;
				closed=0;
				for (int i=0; i<k.length();i++){
					if (k.charAt(i)=='('){
						open++;
					}
					else if(k.charAt(i)==')'){
						closed++;
					}
					
					if (open == 1 && closed == 1 && first==true){
						String firstcity=k.substring(0,i+1);
						String secondcity=k.substring(i+3);
						String[] tuple1 = firstcity.split("\\(");
						String[] tuple2 = secondcity.split("\\(");
						tuple1[0]=tuple1[0].trim();
						tuple1[1]=tuple1[1].substring(0, tuple1[1].length()-1);
						tuple2[0]=tuple2[0].trim();
						tuple2[1]=tuple2[1].substring(0, tuple2[1].length()-1);
						if (cityfinder(tuple1[0])!=null  && cityfinder(tuple2[0])!=null){
						
						addEdge(cityfinder(tuple1[0]),cityfinder(tuple2[0]),2);}
						else{System.out.println(tuple1[0]+","+tuple2[0]);}
						//System.out.println(tuple2[0]);
						//System.out.println(secondcity);	
						first=false;
					}
				}

			}

			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return current;//.toArray(new Restaurant[current.size()]);											//returns the arraylist
	}
	/*
	 * takes a string and returns a city from citylist
	 */
	public static City cityfinder(String x){
		for (int i=0;i<citylist.size();i++){
			if (citylist.get(i).name.equalsIgnoreCase(x)){
				//System.out.print(x);
				return citylist.get(i);
			}
		}
		return null;
	}

}
// store meal choices in priority queues
// when you go to a city compare the tops of the three stacks
// pop off lowest price