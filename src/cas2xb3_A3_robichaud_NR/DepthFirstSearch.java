package cas2xb3_A3_robichaud_NR;

public class DepthFirstSearch {
	private City c;
	private int count;
    public DepthFirstSearch(City G) {
        G.visited=true;
        dfs(G);
    }
	
	
	
	private void dfs(City G) { 
        count++;  
        if (G.adjList == null){
        	return;
        }
        for (int i=0;i<G.adjList.size();i++){
        	Edge e = G.adjList.get(i);
        	if (e.v != G){
        		City c = e.v;
        	}
        	else{
        		City c = e.w;
        	}
        	if (c.visited==false){
        		dfs(c);
        	
        	}
        			
        }
      
    }


}
