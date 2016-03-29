package cas2xb3_A3_robichaud_NR;

import java.util.Stack;

public class ShortestPath {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    public ShortestPath(int s) {
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

        // check optimality conditions
        
    }
    public void printstuff(){
    	//for (int i=0;i<edgeTo.length;i++){
    	//System.out.print();
    	//}
    }

    // relax edge e and update pq if changed

    private void relax(Edge e) {
        int v = e.v.index, w = e.w.index;
        if (distTo[w] > distTo[v] + e.weightToW()) {
            distTo[w] = distTo[v] + e.weightToW();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
       
    }
    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) {
        	System.out.print("nopath");
        	return null;
        }
        Stack<Edge> path = new Stack<Edge>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.v.index]) {
            path.push(e);
        }
        //Edge p = new Edge(FindPath.citylist.get(0),FindPath.citylist.get(1),2.0);
        //path.push(p);
        return path;
    }
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

}
