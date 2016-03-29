package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

public class BreadthFirstSearch {
	private ArrayList<City> path;
	private final City source;
	private final City destination;


	public BreadthFirstSearch(City source, City Destination) {
		this.destination=Destination;
		this.source = source;
		bfs(source, Destination);
	}

	private ArrayList<City> retPath(City x) {
		ArrayList<City> path = new ArrayList<City>();
		if (x.equals(source)) { // if reach source, add it and return path
			// System.out.println("1" + x.name);
			path.add(x);
			return path;
		} else {
			if (x.edgeTo.w.equals(x)) {
				// System.out.println("2" +x.name);
				path.add(x);
				retPath(x.edgeTo.v);
			} else if (x.edgeTo.v.equals(x)) {
				// System.out.println("3" +x.name);
				path.add(x);
				retPath(x.edgeTo.w);
			}
		}
		return path;
	}

	private void bfs(City G, City Destination) {
		// City c; // the next city to be searched
		ArrayList<City> q = new ArrayList<City>();
		for (int i = 0; i < FindPath.citylist.size(); i++) {
			FindPath.citylist.get(i).distTo = 1000000; // couldn't figure out
														// how to use infinity
		}
		G.visited=true;
		G.distTo = 0;
		q.add(G);
		while (!q.isEmpty()) {
			City p = q.get(q.size() - 1); // save top of stack
			q.remove(q.size() - 1); // pop top of stack
			if (p.name.equalsIgnoreCase(Destination.name)) { // if G is the
				p.visited=true;	
				setPath(retPath(p));	
				//path=q;
				return;
				//			
			}
			for (int i = 0; i < p.adjList.size(); i++) { 
				

				if (p.adjList.get(i).v != p) {
					if (p.adjList.get(i).v.visited == false) {						
						p.adjList.get(i).v.edgeTo = p.adjList.get(i);
						p.adjList.get(i).v.distTo = p.distTo + 1;
						p.adjList.get(i).v.visited = true;
						q.add(p.adjList.get(i).v);
					}
				}

				else if (p.adjList.get(i).w != p) {
					if (p.adjList.get(i).w.visited == false) {
						p.adjList.get(i).w.edgeTo = p.adjList.get(i);
						p.adjList.get(i).w.distTo = p.distTo + 1;
						p.adjList.get(i).w.visited = true;
						q.add(p.adjList.get(i).w);
					}
				}
			}

		}
		

	}
	public Boolean hasPathTo(){
		return destination.visited;
	}

	public ArrayList<City> getPath() {
		return path;
	}

	public void setPath(ArrayList<City> path) {
		this.path = path;
	}
/*
 * reverse the effects of the sort on each city object
 */
	public void restore() {
		for (int i=0;i<FindPath.citylist.size();i++){
			FindPath.citylist.get(i).visited = false;
			FindPath.citylist.get(i).distTo = 100000000;
			FindPath.citylist.get(i).edgeTo = null;
		}
	}
}
