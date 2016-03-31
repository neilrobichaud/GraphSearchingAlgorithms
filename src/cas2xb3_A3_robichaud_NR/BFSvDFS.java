package cas2xb3_A3_robichaud_NR;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BFSvDFS {
	private BreadthFirstSearch bfs;
	private DepthFirstSearch dfs;
	ArrayList<City> bpath;
	ArrayList<City> dpath;
	public static void addEdge(City x, City y) {
		Edge e = new Edge(x, y);
		Edge e2 = new Edge(y,x);
		x.adjList.add(e);
		y.adjList.add(e2);
	}

	@Before
	public void setUp() throws Exception {
		ArrayList<City> citylist = new ArrayList<City>();
		City a = new City(1.0,1.0,"1","y","5",3);
		City b = new City(1.0,1.0,"2","y","5",3);
		City c = new City(1.0,1.0,"3","y","5",3);
		citylist.add(a);
		citylist.add(b);
		citylist.add(c);		
		addEdge(a,b);
		addEdge(b,c);
		addEdge(c,a);



		bfs = new BreadthFirstSearch(citylist,citylist.get(0));
		bpath =bfs.pathTo(citylist.get(2));
		bfs.restore();
		
		dfs = new DepthFirstSearch(citylist,citylist.get(0));
		dpath =dfs.pathTo(citylist.get(2));
		dfs.restore();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDisjointb() {
		for(int i=0;i<bpath.size();i++){
			City g = bpath.remove(i);				//remove the city and then see if the list still contains it, if not then it is disjoint
			assertTrue(!bpath.contains(g));
		}	
	}
	
	@Test
	public void testDisjointd() {
		for(int i=0;i<dpath.size();i++){
			City g = dpath.remove(i);
			assertTrue(!dpath.contains(g));
		}		
	}
	
	@Test
	public void testEdgesD(){	
		int count = 0;
		for (int i=0;i<dpath.size()-1;i++){				//for each city except the last
			boolean found = false;
			for(Edge e: dpath.get(i).adjList){			//for each edge in the adjList
				if(e.w == dpath.get(i+1)){				//if the edge leads to the next city
					found=true;							//found ==true
				}
			}
			if (found==true){
				count++;
			}	
			
		}					
		assertTrue(count==dpath.size()-1);
	}
	
	@Test
	public void testEdgesB(){	
		int count = 0;
		for (int i=0;i<bpath.size()-1;i++){				//for each city except the last
			boolean found = false;
			for(Edge e: bpath.get(i).adjList){			//for each edge in the adjList
				if(e.w == bpath.get(i+1)){				//if the edge leads to the next city
					found=true;							//found ==true
				}
			}
			if (found==true){
				count++;
			}	
			
		}					
		assertTrue(count==bpath.size()-1);
	}
	

}
