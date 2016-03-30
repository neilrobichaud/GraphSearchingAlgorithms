package cas2xb3_A3_robichaud_NR;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FindPath {
	static Map<String, String> menuDict;
	public static ArrayList<String> MDmeals; // sorted arraylist of meals in
												// decreasing order of price
	public static ArrayList<String> BKmeals;
	public static ArrayList<String> Wmeals;
	public static Restaurant[] rlist;
	public static ArrayList<City> citylist;
	public static BreadthFirstSearch breadthpath;
	public static DepthFirstSearch depthpath;
	public static Map<String, String> gasStateDictionary;
	public static ArrayList<Double> mcdMenu = new ArrayList<Double>();
	public static ArrayList<Double> wendyMenu= new ArrayList<Double>();
	public static ArrayList<Double> bkMenu= new ArrayList<Double>();
	public static Double[] mcdList;
	public static Double[] wendyList;
	public static Double[] bkList;

	public static void main(String[] args) {

		ArrayList<Restaurant> bklist = readRestaurantFromFile("2XB3_A3_DataSets/burgerking.csv");
		ArrayList<Restaurant> mcdlist = readRestaurantFromFile("2XB3_A3_DataSets/mcdonalds.csv");
		ArrayList<Restaurant> wendyslist = readRestaurantFromFile("2XB3_A3_DataSets/wendys.csv");
		
		ArrayList<Restaurant> allRList = new ArrayList<Restaurant>();
		allRList.addAll(bklist);
		allRList.addAll(mcdlist);
		allRList.addAll(wendyslist);
		
		rlist = allRList.toArray(new Restaurant[allRList.size()]);
		InsertionSort.sort(rlist);
		
		gasStateDictionary = readGasFromFile("2XB3_A3_DataSets/StateGasPrice.csv");
		
		readMenuFromFile("2XB3_A3_DataSets/menu.csv");
		
		
		citylist = new ArrayList<City>();
		citylist = readCityFromFile("2XB3_A3_DataSets/zips1990.csv");
		
		readEdgeFromFile("2XB3_A3_DataSets/connectedCities.txt");
		
		OutputFileCreate();
		
		
//		City city1 = cityfinder("chicago","il");
//		City city2 = cityfinder("dallas","tx");
//		ShortestPath spath = new ShortestPath(city1.index);
//		Iterable<Edge> x = spath.pathTo(city2.index);
//		
//		
//		DepthFirstSearch q = new DepthFirstSearch(cityfinder("chicago","il"));
//		Iterable<City> g = q.pathTo(cityfinder("dallas","tx"));
//		System.out.println();
//		for (City c: g){
//			System.out.print(c.name+ "--> ");
//		}
//		q.restore();
//		System.out.println();
//		
//		BreadthFirstSearch breadthpath = new BreadthFirstSearch(cityfinder("chicago","il"));
//		Iterable<City> b = breadthpath.pathTo(cityfinder("dallas","tx"));
//		
//		for(City p: b){
//			System.out.print(p.name + "--> ");
//		}
//		breadthpath.restore();
//
//		System.out.println();
//
//		for (Edge e: x){
//			System.out.print(e.v.name +" --> ");			
//		}
//		System.out.print(menuDict.get("6.39"));

	}
/*
 * reads the input cities and runs bfs & dfs, storing the paths in the output file
 */
	public static void OutputFileCreate() {
		ArrayList<String[]> inputcities = readInTxt();
		
		breadthpath = new BreadthFirstSearch(cityfinder(inputcities.get(0)[0], inputcities.get(0)[1]));
		Iterable<City> b = breadthpath.pathTo(cityfinder(inputcities.get(1)[0], inputcities.get(1)[1]));
		String bfwrite = "";
		for(City c: b){
			bfwrite = bfwrite + c.name + ",";
		}
		breadthpath.restore();
	
		
		DepthFirstSearch depthpath = new DepthFirstSearch(cityfinder(inputcities.get(0)[0], inputcities.get(0)[1]));
		Iterable<City> d = depthpath.pathTo(cityfinder(inputcities.get(1)[0], inputcities.get(1)[1]));
		String dfwrite = "";
		for(City c: d){
			dfwrite = dfwrite + c.name + ",";
		}
		depthpath.restore();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();													//formats doubles to money

		City city1 = cityfinder(inputcities.get(0)[0], inputcities.get(0)[1]);
		City city2 = cityfinder(inputcities.get(1)[0], inputcities.get(1)[1]);
		ShortestPath spath = new ShortestPath(city1.index);
		Iterable<Edge> x = spath.pathTo(city2.index);

		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("a3_out.txt")); // create
																	// output.txt
		} catch (IOException e) {
			
		}

		try {
			bw.write("BFS: ");
			bw.write(bfwrite);
			bw.append('\n');
			bw.write("DFS: ");
			bw.write(dfwrite);
			bw.append('\n');
			bw.append('\n');
			bw.write(String.format("%20s %40s %20s %20s %20s \r\n", "City", "Meal Choice", "Cost of Meal", "Cost of Fuel", "Total Cost"));
			bw.write(String.format("%20s %40s %20s %20s %20s \r\n", city2.name + " "+city2.state, "---", "$0.00", "$0.00", "$0.00"));
			for(Edge e: x){
			String totalCost = formatter.format(e.weightWithFood());
			String city = e.v.name;
			String mealPrice = (formatter.format(e.foodMoney));
			String mealChoice = e.mealChoice;
			String fuelCost = formatter.format(e.gasMoney);
			
			bw.write(String.format("%20s %40s %20s %20s %20s \r\n", city, mealChoice, mealPrice, fuelCost, totalCost));
			}
			

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//printEdges();
	}

	/*
	 * adds an edge between two cities with the given weight
	 */
	public static void addEdge(City x, City y) {
		Edge e = new Edge(x, y);
		Edge e2 = new Edge(y,x);
		x.adjList.add(e);
		y.adjList.add(e2);
	}

	/*
	 * reads the a3_in.txt file and returns arrayList of [city,state]
	 */
	public static ArrayList<String[]> readInTxt() {
		ArrayList<String[]> lst = new ArrayList<String[]>();
		Scanner input;
		try {
			input = new Scanner(new File("a3_in.txt"));
			while (input.hasNextLine()) {
				String k = input.nextLine();
				String[] a = k.split(",");
				lst.add(a);
			}

			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}

	/*
	 * reads the restaurants in and returns arrayList of restaurants
	 */
	public static ArrayList<Restaurant> readRestaurantFromFile(String filename) {
		Scanner input;
		ArrayList<Restaurant> current = new ArrayList<Restaurant>();
		try {
			input = new Scanner(new File(filename));
			while (input.hasNextLine()) {
				String k = input.nextLine();
				String[] l = k.split(",");

				Restaurant a = new Restaurant(Math.abs(Double.parseDouble(l[0])), Double.parseDouble(l[1]), l[2]);
				current.add(a);
			}

			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return current;
		
	}

	public static void readMenuFromFile(String filename) {
		menuDict = new HashMap<String, String>();
		Scanner input;
		try {
			input = new Scanner(new File(filename));
			input.nextLine();
			while (input.hasNextLine()) {
				String k = input.nextLine();
				String[] l = k.split(",");
				l[2] = l[2].replaceAll("[^\\d.]+", "");
				double price = Double.parseDouble(l[2]);
				menuDict.put(l[2], l[1]);									//a dictionary relating prices to meal names
				if (l[0].contains("McDonald")) {
					
					mcdMenu.add(price); // saves
																				// menu
																				// prices
																				// to
																				// their
																				// respective
																				// lists
				} else if (l[0].contains("Burger King")) {
					bkMenu.add(Double.parseDouble(l[2]));
				} else if (l[0].contains("Wendy")) {					
					wendyMenu.add(Double.parseDouble(l[2]));
				}
			}

			input.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		wendyList = wendyMenu.toArray(new Double[wendyMenu.size()]); // sort all
																		// the
																		// menus
		InsertionSort.sort(wendyList);
		wendyList = reverser(wendyList);
		wendyMenu = new ArrayList<Double>(Arrays.asList(wendyList));
		//System.out.print("wendymenu size: " + wendyMenu.size());
		mcdList = mcdMenu.toArray(new Double[mcdMenu.size()]);
		InsertionSort.sort(mcdList);
		mcdList = reverser(mcdList);
		mcdMenu = new ArrayList<Double>(Arrays.asList(mcdList));
		//System.out.print("bkmenu size: " + bkMenu.size());
		bkList = bkMenu.toArray(new Double[bkMenu.size()]);
		InsertionSort.sort(bkList);
		bkList = reverser(bkList);
		bkMenu = new ArrayList<Double>(Arrays.asList(bkList));
		//System.out.print("mcdmenu size: " + mcdMenu.size());
	}

	/*
	 * method to reverse an array
	 */
	public static Double[] reverser(Double[] a) {
		for (int i = 0; i < a.length / 2; i++) {
			double temp = a[i];
			a[i] = a[a.length - i - 1];
			a[a.length - i - 1] = temp;
		}
		return a;
	}

	public static ArrayList<City> readCityFromFile(String filename) {
		Scanner input;
		ArrayList<City> current = new ArrayList<City>();
		try {
			input = new Scanner(new File(filename));
			input.nextLine(); // skip first row of headers
			while (input.hasNextLine()) {

				String k = input.nextLine();
				String[] l = k.split(",");
				if (cityfinder(l[3], l[2]) == null) { // if city is not already
														// in citylist
					String gasPrice = (gasStateDictionary.get(l[2]));
					if (gasPrice == null) {
						gasPrice = "50"; // default price, to prevent errors
					}
					int index = current.size();
					City a = new City(Double.parseDouble(l[4]), Double.parseDouble(l[5]), l[3], l[2], gasPrice, index);
					current.add(a);
				}
			}

			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return current;
	}

	/*
	 * reads edges from file and adds them to their respective cities
	 */
	public static void readEdgeFromFile(String filename) {
		Scanner input;
		int open;
		int closed;
		Boolean first;

		try {
			input = new Scanner(new File(filename));

			while (input.hasNextLine()) {
				String k = input.nextLine();
				first = true;
				open = 0;
				closed = 0;
				for (int i = 0; i < k.length(); i++) {
					if (k.charAt(i) == '(') {
						open++;
					} else if (k.charAt(i) == ')') {
						closed++;
					}

					if (open == 1 && closed == 1 && first == true) {
						String firstcity = k.substring(0, i + 1);
						String secondcity = k.substring(i + 3);
						String[] tuple1 = firstcity.split("\\(");
						String[] tuple2 = secondcity.split("\\(");
						tuple1[0] = tuple1[0].trim();
						tuple1[1] = tuple1[1].substring(0, tuple1[1].length() - 1);
						String[] statelist1 = tuple1[1].split(",");
						tuple2[0] = tuple2[0].trim();
						tuple2[1] = tuple2[1].substring(0, tuple2[1].length() - 1);
						String[] statelist2 = tuple2[1].split(",");
						Boolean found1 = false;
						Boolean found2 = false;
						int ind1 = 0;
						int ind2 = 0;
						for (int q = 0; q < statelist1.length; q++) {
							if (cityfinder(tuple1[0], statelist1[q]) != null) {
								found1 = true;
								ind1 = q;
							}
						}
						for (int q = 0; q < statelist2.length; q++) {
							if (cityfinder(tuple2[0], statelist2[q]) != null) {
								found2 = true;
								ind2 = q;
							}
						}

						if (found1 == true && found2 == true) {
							addEdge(cityfinder(tuple1[0], statelist1[ind1]), cityfinder(tuple2[0], statelist2[ind2]));
						} else {
							System.out.println(tuple1[0] + "," + tuple2[0]);
						}

						first = false;
					}
				}

			}

			input.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	/*
	 * reads the gas data and return a dictionary of [State, Price]
	 */
	public static Map<String, String> readGasFromFile(String filename) {
		Scanner input;
		Map<String, String> dictionary = new HashMap<String, String>();
		try {
			input = new Scanner(new File(filename));
			input.nextLine(); // skip first row of headers
			while (input.hasNextLine()) {
				String k = input.nextLine();
				k = k.trim();
				String[] l = k.split(",");

				dictionary.put(l[0], l[1]);
			}

			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dictionary;// .toArray(new Restaurant[current.size()]); //returns
							// the arraylist
	}

	/*
	 * takes a string and returns a city from citylist
	 */
	public static City cityfinder(String x, String state) {
		for (int i = 0; i < citylist.size(); i++) {
			if (citylist.get(i).name.equalsIgnoreCase(x) && citylist.get(i).state.equalsIgnoreCase(state)) {
				// System.out.print(x);
				return citylist.get(i);
			}
		}
		return null;
	}

}
//TODO
/*

 * write answers to written question
 * junit test cases
 */
