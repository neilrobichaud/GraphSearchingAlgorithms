package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;
import java.util.Stack;

public class BreadthFirstSearch {
	private ArrayList<City> path;
	private final City source;

	public BreadthFirstSearch(City source) {
		this.source = source;
		for (int i = 0; i < FindPath.citylist.size(); i++) {
			FindPath.citylist.get(i).distTo = 10000000; // couldn't figure out
														// how to use infinity
		}
		bfs(source);
	}

	private void bfs(City G) {
		// City c; // the next city to be searched
		ArrayList<City> q = new ArrayList<City>();

		G.visited = true;
		G.distTo = 0;
		q.add(G);
		while (!q.isEmpty()) {
			City p = q.get(q.size() - 1); // save top of stack
			q.remove(q.size() - 1); // pop top of stack

			for (int i = 0; i < p.adjList.size(); i++) {
				if (p.adjList.get(i).w.visited == false) {
					p.adjList.get(i).w.edgeTo = p.adjList.get(i);
					p.adjList.get(i).w.distTo = p.distTo + 1;
					p.adjList.get(i).w.visited = true;
					q.add(p.adjList.get(i).w);
				}
			}

		}

	}
	
    public Iterable<City> pathTo(City v) {
        if (!hasPathTo(v)) return null;
        Stack<City> path = new Stack<City>();
        City x;
        for (x = v; x.distTo != 0; x = x.edgeTo.v)
            path.push(x);
        path.push(x);
        return path;
    }
    
    public boolean hasPathTo(City v) {
        return v.visited;
    }

	public ArrayList<City> getPath() {
		return path;
	}



	/*
	 * reverse the effects of the sort on each city object
	 */
	public void restore() {
		for (int i = 0; i < FindPath.citylist.size(); i++) {
			FindPath.citylist.get(i).visited = false;
			FindPath.citylist.get(i).distTo = 100000000;
			FindPath.citylist.get(i).edgeTo = null;
		}
	}
}
