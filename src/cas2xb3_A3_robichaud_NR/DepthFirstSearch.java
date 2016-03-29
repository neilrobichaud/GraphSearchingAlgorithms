package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

public class DepthFirstSearch {
	// private City c;

	private ArrayList<City> path;
	private final City source;
	private final City Destination;
	int count;

	public DepthFirstSearch(City source, City Destination) {
		count = 0;
		this.Destination = Destination;
		this.source = source;

		dfs(source, Destination);
	}
/*
 * return the path from this node back to the source
 */
	private ArrayList<City> retPath(City x) {
		ArrayList<City> path = new ArrayList<City>();
		if (x.equals(source)) { // if reach source, add it and return path
			path.add(x);
			return path;
		} else {
			
			if (x.edgeTo.w.equals(x)) {
				//System.out.println("x");
				path.add(x);
				retPath(x.edgeTo.v);
			} else if (x.edgeTo.v.equals(x)) {
				//System.out.println("y");
				path.add(x);
				retPath(x.edgeTo.w);
			}
		}
		return path;
	}
/*
 * recursive dfs on citylist
 */
	private void dfs(City G, City Destination) {
		G.visited = true;
		count++;
		if (G.name.equalsIgnoreCase(Destination.name)) { // if G is the
															// destination
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

					if (G.adjList.get(i).v != G) {
						//System.out.print("x");
						if (G.adjList.get(i).v.visited == false) {
							G.adjList.get(i).v.edgeTo = G.adjList.get(i);
							dfs(G.adjList.get(i).v, Destination);
						}
					}

					else if (G.adjList.get(i).w != G) {
						System.out.print("y");

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
		return this.path;
	}

	public Boolean hasPathTo() {
		return Destination.visited;
	}

	public void setPath(ArrayList<City> path) {
		this.path = path;
	}

	public int count() {
		return count;
	}
/*
 * reverse the effects of the sort on each city object
 */
	public void restore() {
		for (int i = 0; i < FindPath.citylist.size(); i++) {
			FindPath.citylist.get(i).visited = false;
			FindPath.citylist.get(i).distTo = 100000000; 			//used because its simpler than Math.Infinity but can be scaled up
			FindPath.citylist.get(i).edgeTo = null;
		}
	}

}
