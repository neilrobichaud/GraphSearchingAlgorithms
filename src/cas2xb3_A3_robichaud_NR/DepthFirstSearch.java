package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch {
	private ArrayList<City> citylist;

	private ArrayList<City> path;
	private final City source;	
	int count;

	public DepthFirstSearch(ArrayList<City> citylist,City source) {		count = 0;
		this.citylist = citylist;
		this.source = source;

		dfs(source);
	}


	/*
	 * recursive dfs on citylist
	 */
	private void dfs(City G) {
		G.visited = true;
		count++;
		for (int i = 0; i < G.adjList.size(); i++) { // loop through edges in G's adjList
			if (G.adjList.get(i).w.visited == false) {
				G.adjList.get(i).w.edgeTo = G.adjList.get(i);
				dfs(G.adjList.get(i).w);
			}

		}

	}
	
    public ArrayList<City> pathTo(City v) {
        if (!hasPathTo(v)) return null;
        ArrayList<City> path = new ArrayList<City>();
        for (City x = v; x != source; x = x.edgeTo.v)
            path.add(x);
        path.add(source);
        return path;
    }
    public boolean hasPathTo(City v) {
        return v.visited;
    }

	public ArrayList<City> getPath() {
		return this.path;
	}

	/*
	 * reverse the effects of the sort on each city object
	 */
	public void restore() {
		for (int i = 0; i < citylist.size(); i++) {
			citylist.get(i).visited = false;
			citylist.get(i).distTo = 100000000; // used because its
															// simpler than
															// Math.Infinity but
															// can be scaled up
			citylist.get(i).edgeTo = null;
		}
	}

}
