package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

public class DepthFirstSearch {
	// private City c;

	private ArrayList<City> path=new ArrayList<City>();
	private final City source;
	private final City Destination;
	int count;
	private final ArrayList<City> prevConfig = FindPath.citylist;
	public DepthFirstSearch(City source, City Destination) {
		count=0;
		this.Destination=Destination;
		this.source = source;
		dfs(source, Destination);
	}
	
	private ArrayList<City> retPath(City x) {
		
		if (x.equals(source)) {		// if reach source, add it and return path
			//System.out.println("1" + x.name);
			path.add(x);
			return path;
		} else {									
			if (x.edgeTo.w.equals(x)) {	
				//System.out.println("2" +x.name);
				path.add(x);
				retPath(x.edgeTo.v);
			}
			else if(x.edgeTo.v.equals(x)){
				//System.out.println("3" +x.name);
				path.add(x);
				retPath(x.edgeTo.w);
			}
		}
		return path;
	}

	private void dfs(City G, City Destination) {
		//City c; // the next city to be searched
		G.visited = true;
		count++;
		if (G.name.equalsIgnoreCase(Destination.name)) { // if G is the destination
			setPath(retPath(G));
			return;
		} else {

			if (G.adjList.size() == 0) { // if G is a leaf
				return;
			} else {

				for (int i = 0; i < G.adjList.size(); i++) { // loop through
																// edges
																// in G
																// adjList
					
					if (G.adjList.get(i).v != G){
						if(G.adjList.get(i).v.visited == false) {
							G.adjList.get(i).v.edgeTo = G.adjList.get(i);	
							dfs(G.adjList.get(i).v, Destination);
						}	
					}		
		
					else if (G.adjList.get(i).w != G){
						if (G.adjList.get(i).w.visited == false) {						
							G.adjList.get(i).w.edgeTo = G.adjList.get(i);						
							dfs(G.adjList.get(i).w, Destination);
						}
					}
				}
			}
		}

	}

	public ArrayList<City> getPath() {
		return path;
	}
	public Boolean hasPathTo(){
		return Destination.visited;
	}
	public void setPath(ArrayList<City> path) {
		this.path = path;
	}
	public int count(){
		return count;
	}
	public void restore(){
		FindPath.citylist = prevConfig;
	}

}
