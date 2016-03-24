package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;

public class Graph {


	public void addEdge(int v, int w) {
		adj[v].add(w);
		E++;
	}

	public Iterable<Integer> adj(int v) {
		return adj[v];
	}


}