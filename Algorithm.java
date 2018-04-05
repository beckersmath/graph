import java.util.HashSet;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.Iterator;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Collections;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
* The Algorithms API contains many algorithms that are common among graphs.
* The API allows these algorithms to work on many flavors of graphs and are
* are optimized to work with specific cases such as weighted graphs.
* @author Tyler Townsend
* @version 0.1
* @since 2018-04-05
*/

public class Algorithm {

	// Make a Tree class that inherits from Graph
	public static Set<Edge> bfs(Graphs graph, int root) {
		return bfs(graph,
		Node.makeNode(root), new HashSet<Node>(graph.size()));
	}

	private static Set<Edge> bfs (Graphs graph, Node root, HashSet<Node> visited) {
		Queue<Node> q = new ArrayDeque<Node>();
		Set<Edge> t = new HashSet<Edge>();
		q.add(root);
		visited.add(root);
		while(!q.isEmpty()) {

			// process node
			Node u = q.poll();
			for (Edge e : graph.edges(u)) {
				Node v = e.target();
				if (!visited.contains(v)) {
					visited.add(v);
					q.add(v);
					t.add(e);
				}
			}
		}
		return t;
	}

	public static List<Node> dfs(Graphs graph, int root) {
		List<Node> t = new LinkedList<Node>();
		dfs(graph, Node.makeNode(root), new HashSet<Node>(graph.size()), t);
		return t;
	}

	private static void dfs(Graphs graph, Node root, HashSet<Node> visited, List<Node> t) {
		t.add(root);
		visited.add(root);
		for (Edge e : graph.edges(root)) {
			dfs(graph, e.target(), visited, t);
		}
	}

	public static boolean bipartite(Graphs graph) {

		HashSet<Node> red = new HashSet<Node>();
		HashSet<Node> visited = new HashSet<Node>(graph.size());

		Queue<Node> q = new ArrayDeque<Node>();
		Node start = randomNode(graph);
		visited.add(start);
		// put start in red set
		red.add(start);
		q.add(start);
		while (!q.isEmpty()) {
			Node u = q.remove();
			for (Edge e : graph.edges(u)) {
				Node v = e.target();
				// The node is not in a layer so color it accordingly
				if (!visited.contains(v)) {
					visited.add(v);
					if (!red.contains(u))
						red.add(v);
				}
				// Check if the node v is colored the same as u
				if (red.contains(u) && red.contains(v) || 
					!red.contains(u) && !red.contains(v)) return false;
			}
		}

		return true;
	}

	/*
	* Idea: return set of connected components as graphs
	*
	*/
	public static int numberOfComponets (Graphs graph) {
		ArrayList<List<Node>> comp = new ArrayList<List<Node>>();
		comp = connectedComponents(graph);
		return comp.size();
	}

	public static ArrayList<List<Node>> connectedComponents(Graphs graph) {
		HashSet<Node> visited = new HashSet<Node>(graph.size());
		ArrayList<List<Node>> cc = new ArrayList<List<Node>>();

		List<Node> component = new LinkedList<>();
		for (Node u : graph.nodes()) {
			if (!visited.contains(u)) {
				dfs (graph, u, visited, component);
				cc.add(component);
			}
		}
		return cc;
	}

	public static boolean stronglyConnected(Graphs graph) {
		boolean connected = (numberOfComponets(graph)==1);
		if (!connected) {
			return false;
		}
		if (!graph.directed()) {
			return true;
		}
		return stronglyConnected(graph, randomNode(graph));
	}

	private static boolean stronglyConnected(Graphs graph, Node u) {
		Graphs graphRev = reverseGraph(graph);
		HashSet<Node> visited = new HashSet<Node>(graph.size());
		HashSet<Node> visitedRev = new HashSet<Node>(graph.size());
		bfs(graph, u, visited);
		bfs(graphRev, u, visitedRev);
		if (visited == null || visitedRev == null) return false;
		else if (visited.size() != visitedRev.size()) return false;
		else return visited.containsAll(visitedRev);
	}

	public static List<Node> topologicalSort(DirectedGraph graph) {
		HashMap<Node, Integer> incoming = new HashMap<>(graph.size());
		List<Node> order = new LinkedList<Node>();
		int cnt = 0;
		for (Node u : graph.nodes()) {
			if (!incoming.containsKey(u))
				incoming.put(u, 0);
			for (Edge e : graph.edges(u)) {
				Node v = e.target();
				// incrementDegree(incoming, v);
				if (!incoming.containsKey(v)) {
					incoming.put(v, 1);
				} else {
					incoming.put(v, incoming.get(v)+1);
				}
			}
		}
		Queue<Node> q = new ArrayDeque<Node>();
		for (Node u : graph.nodes()) {
			if (incoming.get(u)==0)
				q.add(u);
		}
		while (!q.isEmpty()) {
			Node u = q.poll();
			order.add(u);
			++cnt;		
			for (Edge e : graph.edges(u)) {
				// decrement
				Node v = e.target();
				incoming.put(v, incoming.get(v)-1);
				if (incoming.get(v) == 0) {
					q.add(v);
				}
			}
		}
		if (cnt!=graph.size()) {
			return null;
		}
		return order;
	}

	public static boolean dag(Graphs graph) {
		if (graph.directed() == false) return false;
		return (topologicalSort((DirectedGraph) graph)==null) ? false : true;
	}

	/** check if un-directed graph is acyclic and connected */
	public static boolean tree(Graphs graph) {

		HashSet<Node> visited = new HashSet<Node>(graph.size());
		/** If the graph has a cycle */
		if (graph.directed()==false) {
			if (cyclic(randomNode(graph), (Graph) graph, visited, Node.makeNode(-1))) {
				return false;
			}
		} else {
			if (cyclic((DirectedGraph) graph)) {
				return false;
			}	
		}

		/** If the graph is disconnected */
		for (Node u : graph.nodes()) {
			if (!visited.contains(u)) {
				System.out.println("disconnected");
				return false;
			}
		}
		return true;
	}

	private static boolean cyclic(Node u, Graph graph, 
									HashSet<Node> visited, Node parent) {
		visited.add(u);
		for (Edge e : graph.edges(u)) {
			Node v = e.target();
			if (!visited.contains(v)) {
				if (cyclic(v, graph, visited, u))
					return true;
			}
			else if (!v.equals(parent))
				return true;
		}
		return false;
	}

	public static boolean cyclic(DirectedGraph graph) {
		HashSet<Node> visited = new HashSet<Node>(graph.size());
		HashSet<Node> recStack = new HashSet<Node>(graph.size());
		for (Node u : graph.nodes()) {
			if (cyclic(graph, randomNode(graph), visited, recStack))
				return true;
		}
		return false;
	}

	private static boolean cyclic (DirectedGraph graph, Node u, 
					HashSet<Node> visited, HashSet<Node> recStack) {
		if (!visited.contains(u)) {
			visited.add(u);
			recStack.add(u);
			for (Edge e : graph.edges(u)) {
				Node v = e.target();
				if (!visited.contains(v) && cyclic(graph, v, visited, recStack))
					return true;
				else if (recStack.contains(v))
					return true;
			}
		}
		recStack.remove(u);
		return false;
	}

	// Implement functions to check if graph is weighted and directed
	// Can implement reverse edge method in in
	public static Graphs reverseGraph(Graphs graph) {
		Graphs reversed = Algorithm.graphType(graph);
		for (Node u : graph.nodes()) {
			for (Edge e : graph.edges(u)) {
				Edge eRev = e.reverseEdge();
				reversed.addEdge(eRev);
			}
		}
		return reversed;
	}

	public static boolean containsPath(Graphs graph, int s, int t) {
		return containsPath(graph, Node.makeNode(s), Node.makeNode(t));
	}

	private static boolean containsPath(Graphs graph, Node s, Node t) {

		HashSet<Node> visited = new HashSet<Node>(graph.size());
		Queue<Node> q = new ArrayDeque<Node>();
		q.add(s);
		visited.add(s);
		while(!q.isEmpty()) {
			Node u = q.poll();
			for (Edge e : graph.edges(u)) {
				Node v = e.target();
				if (v.equals(t)) return true;
				if (!visited.contains(v)) {
					visited.add(v);
					q.add(v);
				}
			}
		}
		return false;
	}

	public static Path getPath(Graphs graph, int s, int t) {
		if (!containsPath(graph, s, t)) return null;
		if (graph.weighted()==true) return getWeightedPath(graph, s, t);
		return getPath(graph, Node.makeNode(s), Node.makeNode(t));
	}	

	private static Path getPath(Graphs graph, Node s, Node t) {
		Path path = Path.makePath(s);
		HashSet<Node> visited = new HashSet<Node>(graph.size());
		HashMap<Node, Edge> parent = new HashMap<Node, Edge>(graph.size());
		Queue<Node> q = new ArrayDeque<Node>();
		q.add(s);
		visited.add(s);
		boolean found = false;
		while(!q.isEmpty() && !found) {
			Node u = q.poll();
			for (Edge e : graph.edges(u)) {
				Node v = e.target();
				if (v.equals(t)) found = true;
				if (!visited.contains(v)) {
					visited.add(v);
					q.add(v);
					parent.put(v, e);
				}
			}
		}
		return constructPath(path, parent, s, t);
	}

	private static Path getWeightedPath(Graphs graph, int s, int t) {
		return getWeightedPath(graph, Node.makeNode(s), Node.makeNode(t));
	}

	private static Path getWeightedPath(Graphs graph, Node s, Node t) {
		HashMap<Node, Double> dist = new HashMap<Node, Double>(graph.size());
		HashSet<Node> visited = new HashSet<Node>(graph.size());
		Path path = Path.makePath(s);
		HashMap<Node, Edge> parent = new HashMap<Node, Edge>(graph.size());
		PriorityQueue<Edge> minheap = new PriorityQueue<Edge>();
 	
 		for (Node u : graph.nodes()) {
 			dist.put(u, 1e9);
 		}

		dist.put(s, 0.0);
		int visitedVertices = 0;
		minheap.add(Edge.makeEdge(s, s, 0));
		boolean found = false;
		while (!minheap.isEmpty() && visitedVertices < graph.size() && !found) {
			Edge e = minheap.remove();
			Node v = e.target();
			if (visited.contains(v)) continue;
			visited.add(v);
			visitedVertices++;
			for (Edge f : graph.edges(v)) {
				if (v.equals(t)) found = true;
				if (!visited.contains(f.target())) {
					dist.put(f.target(), dist.get(f.source()) + f.weight());
					minheap.add(f);
					parent.put(f.target(), f);
				}
			}
		}
		return constructPath(path, parent, s, t);
	}

	private static Path constructPath(Path path, HashMap<Node, Edge> parent, Node s, Node t) {
		Node curr = t;
		while (!parent.get(curr).source().equals(s)) {
			path.add(parent.get(curr));
			curr = parent.get(curr).source();
		}
		path.insert(parent.get(curr).source());
		Collections.reverse(path.get());
		return path;
	}

	public static Map<Node, Double> getDistances(Graphs graph, int source) {
		return getDistances(graph, Node.makeNode(source));
	}

	private static Map<Node, Double> getDistances(Graphs graph, Node source) {

		HashMap<Node, Double> dist = new HashMap<Node, Double>(graph.size());
		HashSet<Node> visited = new HashSet<Node>(graph.size());
		HashMap<Node, Node> parent = new HashMap<Node, Node>(graph.size());
		PriorityQueue<Edge> minheap = new PriorityQueue<Edge>();
 	
 		for (Node u : graph.nodes()) {
 			dist.put(u, 1e9);
 		}
		dist.put(source, 0.0);

		int visitedVertices = 0;
		minheap.add(Edge.makeEdge(source, source, 0));
		while (!minheap.isEmpty() && visitedVertices < graph.size()) {
			Edge e = minheap.remove();
			Node v = e.target();
			if (visited.contains(v)) continue;
			visited.add(v);
			visitedVertices++;

			for (Edge f : graph.edges(v)) {
				if (!visited.contains(f.target())) {
					dist.put(f.target(), dist.get(f.source()) + f.weight());
					minheap.add(f);
					parent.put(f.target(), v);
				}
			}
		}
		return dist;
	}

	private static void printMinimumDistance(Node source, HashMap<Node, Double> dist) {
		for (Node u : dist.keySet()) {
			System.out.println("    Minimum Distance to "+ (char)(u.value().longValue() + 'a') + " : " + dist.get(u));
		}
	}

	public static Set<Edge> mst(Graphs graph) {

		// First check if graph is connnected
		if (Algorithm.numberOfComponets(graph)!=1) return null;
		Set<Edge> mst = new HashSet<Edge>(); 
		int n = graph.size();
		HashSet<Node> visited = new HashSet<Node>(graph.size());
		Node u = Algorithm.randomNode(graph);
		visited.add(u);

		// Add all of v's edge into the priority queue.
		PriorityQueue<Edge> minheap = new PriorityQueue<Edge>();
		for (Edge e : graph.edges(u)) {
			minheap.offer(e);
		}

		int numEdges = 0;
		while (minheap.size()  > 0) {

			// getNext Edge
			Edge e = minheap.poll();
			mst.add(e);
			if (visited.contains(e.source()) && visited.contains(e.target())) 
				continue;

			if (!visited.contains(e.source())) {
				for (Edge adj : graph.edges(e.source()))
					minheap.offer(adj);
				visited.add(e.source());
			} else { 
				for (Edge adj : graph.edges(e.target()))
					minheap.offer(adj);
				visited.add(e.target());
			}

			// Bookeeping
			numEdges ++;
			if (numEdges == n-1) break;
		}
		// Extra check for errors
		return (numEdges == n-1) ? mst : null;
	}

	public static double mstValue(Set<Edge> T) {
		double value = 0;
		for (Edge e : T) {
			value += e.weight(); 
		}
		return value;
	}

	private static Graphs graphType(Graphs graph){
		return graph.directed() ? DirectedGraph.makeGraph() : Graph.makeGraph();
	}

	/** Returns a random node from the graph in O(1) time */
	private static Node randomNode(Graphs graph) {
		return graph.nodes().iterator().next();
	}

	public static void printGraph(Graphs graph) {

		if (graph == null) {
			System.out.println("null pointer dude");
			return;
		}
 
		for (Node u : graph.nodes()) {
			System.out.print(u+": ");
			for (Edge e : graph.edges(u)) {
				System.out.print(e.target()+" ");
			}
			System.out.println("\n");
		}
	}

	public static void printMap(Map<Node, Double> distances) {
		for (Node u : distances.keySet()) {
			System.out.print(u +": " +distances.get(u));
		}
	}

	public static void printPath(Path p) {
		for (Node u : p.get()) {
			System.out.print(u + (u.equals(p.get(p.size()-1)) ? "\n" : " "));
		}
	}

	public static void printList(List<Node> list) {
		for (int i=0; i<list.size(); i++)
			System.out.print(list.get(i) + (i==list.size()-1 ? "\n" : "->"));
	}
}