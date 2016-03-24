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

}
// store meal choices in priority queues
// when you go to a city compare the tops of the three stacks
// pop off lowest price