package cas2xb3_A3_robichaud_NR;

import java.util.ArrayList;
import java.util.Stack;

public class ShortestPath {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    private int source;
    
    public ShortestPath(int s) {
    	this.source = s;
        distTo = new double[FindPath.citylist.size()];
        edgeTo = new Edge[FindPath.citylist.size()];
        for (int v = 0; v < FindPath.citylist.size(); v++)
          distTo[v] = Double.POSITIVE_INFINITY;
       distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(FindPath.citylist.size());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e :	FindPath.citylist.get(v).adjList)
                relax(e);
        }


        
    }

    // relax edge e and update pq if changed

    private void relax(Edge e) {
        int v = e.v.index, w = e.w.index;
        if (distTo[w] > distTo[v] + e.weightToW()) {
            distTo[w] = distTo[v] + e.weightToW();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else  pq.insert(w, distTo[w]);
        }
       
    }
    public ArrayList<Edge> pathTo(int v) {
        if (!hasPathTo(v)) {
        	System.out.print("nopath");
        	return null;
        }
        ArrayList<Edge> path = new ArrayList<Edge>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.v.index]) {
            path.add(e);
        }     

        return path;
    }
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

}
