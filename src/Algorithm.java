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

	private Algorithm() {}

	// TODO Make a Tree class that implements Graphs
	/**
	 * Performs breadth-first search on graph starting at root. Returns a Set of Edges
	 * that form the bread-first search tree.
	 * 
	 * @param graph Graph data structure
	 * @param root The starting vertix in graph
	 * @return The Edges in the bread-first search tree
	 */
	public static Set<Edge> bfs(Graphs graph, int root) {
		return bfs(graph, root, new HashSet<Integer>(graph.size()));
	}

	private static Set<Edge> bfs (Graphs graph, int root, HashSet<Integer> visited) {

		// The set of edges that form the bfs tree
		Set<Edge> tree = new HashSet<Edge>();

		// Set up a queue of layers starting with the root 
		Queue<Integer> q = new ArrayDeque<Integer>();
		q.add(root);
		visited.add(root);

		// traverse through all the layers and mark each visited node
		while(!q.isEmpty()) {

			// process node and add adjacent nodes v to the queue if not
			// visited
			int u = q.poll();
			for (Edge e : graph.edges(u)) {
				int v = e.target();
				if (!visited.contains(v)) {
					visited.add(v);
					q.add(v);
					tree.add(e);
				}
			}
		}
		return tree;
	}

	public static Set<Edge> dfs(Graphs graph, int root) {
		Set<Edge> tree = new HashSet<Edge>();
		dfs(graph, root, new HashSet<Integer>(graph.size()), tree);
		return tree;
	}

	private static Set<Edge> dfs(Graphs graph, int root, 
							HashSet<Integer> visited, Set<Edge> tree) {
		visited.add(root);

		ArrayDeque<Integer> recStack = new ArrayDeque<Integer>();
		while(!recStack.isEmpty()) {

			int currNode = recStack.pop();

			// Add all the neighbors of currentNode to stack if they have
			// not been visited yet.
			for (Edge e : graph.edges(currNode)) {
				int nextNode = e.target();
				if (!visited.contains(nextNode)) {
					visited.add(nextNode);
					recStack.add(nextNode);
					tree.add(e);
				}
			}
		}

		return tree;
	}

	/**
	 * Tests whether graph is bipartite or that the set of nodes may be partitioned
	 * into two sets such that no adjacent nodes are in the same set.
	 * 
	 * @param graph The graph to be testsed
	 * @return A boolean where if true the graph is bipartite.
	 */
	public static boolean bipartite(Graphs graph) {

		HashSet<Integer> red = new HashSet<Integer>();
		HashSet<Integer> visited = new HashSet<Integer>(graph.size());

		Queue<Integer> q = new ArrayDeque<Integer>();
		int start = randomNode(graph);
		visited.add(start);
		// put start in red set
		red.add(start);
		q.add(start);
		while (!q.isEmpty()) {
			int u = q.remove();
			for (Edge e : graph.edges(u)) {
				int v = e.target();
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

	/**
	 * Returns the number of connected components in graph. 
	 * 
	 * @param graph The graph
	 * @return The number of connected components in the graph.
	 */
	public static int numberOfComponets (Graphs graph) {
		List<Set<Edge>> comp = new ArrayList<Set<Edge>>();
		comp = connectedComponents(graph);
		return comp.size();
	}

	/**
	 * Returns a list of the connected component in graph. Each connected
	 * component is represented by a set of Edges.
	 * 
	 * @param graph The graph.
	 * @return A list of connected components.
	 */
	public static List<Set<Edge>> connectedComponents(Graphs graph) {

		// Maintain a set of visited nodes
		HashSet<Integer> visited = new HashSet<Integer>(graph.size());

		// Store the connected components
		List<Set<Edge>> cc = new ArrayList<Set<Edge>>();

		// Perform a depth first search on the network
		// to find all components.
		Set<Edge> component = new HashSet<>();
		for (Integer u : graph.nodes()) {
			if (!visited.contains(u)) {
				cc.add(dfs (graph, u, visited, component));
			}
		}
		return cc;
	}

	/**
	 * Determins if the graph is strongly connected. That is for node s and t, if
	 * there is a path from s to t, then there is a path from t to s.
	 * 
	 * @param graph The graph.
	 * @return A boolean stating wheteher the graph is strongly connected. If strongly
	 * 		   connected, then returns True.
	 */
	public static boolean stronglyConnected(Graphs graph) {
		boolean connected = (numberOfComponets(graph)==1);
		if (!connected) {
			return false;
		}
		// every undirected graph is strongly connected
		if (!graph.directed()) {
			return true;
		}
		return stronglyConnected(graph, randomNode(graph));
	}

	private static boolean stronglyConnected(Graphs graph, int u) {
		// Reverse the graph
		Graphs graphRev = reverseGraph(graph);
		HashSet<Integer> visited = new HashSet<Integer>(graph.size());
		HashSet<Integer> visitedRev = new HashSet<Integer>(graph.size());
		bfs(graph, u, visited);
		bfs(graphRev, u, visitedRev);
		// If the graph and the reversed graph reach all nodes, then
		// the graph is strongly connected.
		if (visited == null || visitedRev == null) return false;
		else if (visited.size() != visitedRev.size()) return false;
		else return visited.containsAll(visitedRev);
	}

	/**
	 * Returns a topological ordering on graph. Returned as a list of 
	 * the Node numbers.
	 * 
	 * @param graph A DirectedGraph 
	 * @return An ordered list of Nodes.
	 */
	public static List<Integer> topologicalSort(DirectedGraph graph) {

		// Store incoming nodes
		HashMap<Integer, Integer> incoming = new HashMap<>(graph.size());

		// List of ordered nodes
		List<Integer> order = new LinkedList<Integer>();

		// Count number of nodes
		int count = 0;

		// For each node in the graph
		// count the number of incoming edges
		for (Integer u : graph.nodes()) {
			if (!incoming.containsKey(u))
				incoming.put(u, 0);
			for (Edge e : graph.edges(u)) {
				int v = e.target();
				if (!incoming.containsKey(v)) {
					incoming.put(v, 1);
				} else {
					incoming.put(v, incoming.get(v)+1);
				}
			}
		}
		// Add all of our 0 incoming nodes into our ArrayDeque
		Queue<Integer> q = new ArrayDeque<Integer>();
		for (Integer u : graph.nodes()) {
			if (incoming.get(u)==0)
				q.add(u);
		}

		// While there are nodes that need to be processed
		// add that to our topological order.
		while (!q.isEmpty()) {
			int u = q.poll();
			order.add(u);
			++count;		
			for (Edge e : graph.edges(u)) {
				// decrement all nodes adjacent to u
				int v = e.target();
				incoming.put(v, incoming.get(v)-1);
				if (incoming.get(v) == 0) {
					q.add(v);
				}
			}
		}
		if (count!=graph.size()) {
			return new LinkedList<Integer>();
		}
		return order;
	}

	/**
	 * Determines if the graph is a directed acyclic graph.
	 *
	 * @param graph The graph.
	 * @return True if the graph is directed and acylic otherwise false.
	 */
	public static boolean dag(Graphs graph) {
		if (graph.directed() == false) return false;
		return 
			(topologicalSort((DirectedGraph) graph).size() == 0) ? false : true;
	}

	/**
	 * Determines if the graph is a tree. For it to be a tree, the graph must
	 * be acyclic and connected.
	 * 
	 * @param graph  The graph.
	 * @return Returns true if the graph is acyclic and connected.
	 */
	public static boolean tree(Graphs graph) {

		HashSet<Integer> visited = new HashSet<Integer>(graph.size());
		/* If the graph has a cycle */
		if (graph.directed()==false) {
			if (cyclic(randomNode(graph), (Graph) graph, visited, -1))
				return false;
		} else {
			if (isTree((DirectedGraph) graph))
				return false;
		}

		/* If the graph is disconnected */
		for (Integer u : graph.nodes()) {
			if (!visited.contains(u)) {
				return false;
			}
		}
		return true;
	}

	/* 
	   Performs DFS on starting at node u. Continuosly check if we have
	   visited a node before. If it is not the parent of the u and we have
	   visited the node before, then the graph contains a cycle. 
	 */
	private static boolean cyclic(Integer u, Graph graph, 
							      HashSet<Integer> visited, int parent) {
		// visited node u
		visited.add(u);
		for (Edge e : graph.edges(u)) {
			Integer v = e.target();
			// 
			if (!visited.contains(v)) {
				if (cyclic(v, graph, visited, u))
					return true;
			}
			else if (!v.equals(parent))
				return true;
		}
		return false;
	}

	private static boolean isTree(DirectedGraph graph) {
		
		HashSet<Integer> visited = new HashSet<>(graph.size());
		return isTree(graph, visited, randomNode(graph));
	}

	private static boolean isTree(DirectedGraph graph, 
								  HashSet<Integer> visited, 
								  Integer currentNode) {

		// mark the node visited
		visited.add(currentNode);

		// for each adjacent node
		for (Edge e : graph.edges(currentNode)) {
			if (visited.contains(currentNode)) return false;
			// Get the destination node of the edge
			Integer nextNode = e.target();
			isTree(graph, visited, nextNode);
		}

		return true;

	}

	/**
	 * Reverse graph out-of-place and returns the new graph with each edge reversed
	 * .
	 * @param  graph The graph to be reversed
	 * @return       A new graph that is original graph with every edge reversed.
	 */
	public static Graphs reverseGraph(Graphs graph) {
		Graphs reversed = Algorithm.graphType(graph);
		for (Integer u : graph.nodes()) {
			for (Edge e : graph.edges(u)) {
				Edge eRev = e.reverseEdge();
				reversed.addEdge(eRev);
			}
		}
		return reversed;
	}


	/**
	 * Returns a boolean indicating whether there is an s-t path in 
	 * graph
	 * .
	 * @param  graph The graph for which an s-t path is being determined.
	 * @param  s     The starting node
	 * @param  t     The terminal node
	 * @return       Returns true if there is a path, otherwise false;
	 */
	public static boolean containsPath(Graphs graph, int s, int t) {

		HashSet<Integer> visited = new HashSet<Integer>(graph.size());
		Queue<Integer> q = new ArrayDeque<Integer>();
		q.add(s);
		visited.add(s);
		while(!q.isEmpty()) {
			Integer u = q.poll();
			for (Edge e : graph.edges(u)) {
				Integer v = e.target();
				if (v.equals(t)) return true;
				if (!visited.contains(v)) {
					visited.add(v);
					q.add(v);
				}
			}
		}
		return false;
	}

	/**
	 * Constructs the shortest path from the startNode to the terminalNode in graph
	 * 
	 * @param graph The graph
	 * @param startNode The node which will start this Path
	 * @param terminalNode The node which will end this Path
	 * @return The Path from startNode to terminalNode
	 */
	public static Path getPath(Graphs graph, int startNode, int terminalNode) {
		if (!containsPath(graph, startNode, terminalNode)) return null;
		if (graph.weighted()==true) 
			return getWeightedPath(graph, startNode, terminalNode);
		return getUnweightedPath(graph, startNode, terminalNode);
	}	

	/*
	   Performs BFS which returns the shortest path from s to t
	 */
	private static Path getUnweightedPath(Graphs graph, int s, int t) {
		Path path = Path.makePath(s);
		HashSet<Integer> visited = new HashSet<Integer>(graph.size());
		HashMap<Integer, Edge> parent = new HashMap<Integer, Edge>(graph.size());
		Queue<Integer> q = new ArrayDeque<Integer>();
		q.add(s);
		visited.add(s);
		boolean found = false;
		while(!q.isEmpty() && !found) {
			Integer u = q.poll();
			for (Edge e : graph.edges(u)) {
				Integer v = e.target();
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
		HashMap<Integer, Double> dist = new HashMap<Integer, Double>(graph.size());
		HashSet<Integer> visited = new HashSet<Integer>(graph.size());
		Path path = Path.makePath(s);
		HashMap<Integer, Edge> parent = new HashMap<Integer, Edge>(graph.size());
		PriorityQueue<Edge> minheap = new PriorityQueue<Edge>();
 	
 		for (Integer u : graph.nodes()) {
 			dist.put(u, 1e9);
 		}

		dist.put(s, 0.0);
		int visitedVertices = 0;
		minheap.add(Edge.makeEdge(s, s, 0));
		boolean found = false;
		while (!minheap.isEmpty() && visitedVertices < graph.size() && !found) {
			Edge e = minheap.remove();
			Integer v = e.target();
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

	/*
	  Constructs the path by tracing back from each node to its parent then reverses the order
	 */
	private static Path constructPath(Path path, HashMap<Integer, Edge> parent, int s, int t) {
		int curr = t;
		while (!parent.get(curr).source().equals(s)) {
			path.add(parent.get(curr));
			curr = parent.get(curr).source();
		}
		path.insert(parent.get(curr).source());
		Collections.reverse(path.get());
		return path;
	}

	/**
	 * Returns a map of the distance to each reachable node in graph from node source. 
	 * Unreachable nodes have a distance of 1 billion. This algorithm implements Dijkstras
	 * method of all shortest paths.
	 * 
	 * @param graph The graph.
	 * @param source The starting node for all paths.
	 * @return The distance to each node.
	 */
	private static Map<Integer, Double> getDistances(Graphs graph, int source) {

		HashMap<Integer, Double> dist = new HashMap<Integer, Double>(graph.size());
		HashSet<Integer> visited = new HashSet<Integer>(graph.size());
		HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>(graph.size());
		PriorityQueue<Edge> minheap = new PriorityQueue<Edge>();
 	
 		for (Integer u : graph.nodes()) {
 			dist.put(u, 1e9);
 		}
		dist.put(source, 0.0);

		int visitedVertices = 0;
		minheap.add(Edge.makeEdge(source, source, 0));
		while (!minheap.isEmpty() && visitedVertices < graph.size()) {
			Edge e = minheap.remove();
			int v = e.target();
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

	private static void printMinimumDistance(int source, HashMap<Integer, Double> dist) {
		for (Integer u : dist.keySet()) {
			System.out.println("    Minimum Distance to "+ (char)(u.longValue() + 'a') + " : " + dist.get(u));
		}
	}

	/**
	 * Implements Prims algorithm for finding the minimum spanning tree. 
	 * 
	 * @param graph The graph for which an mst will be found
	 * @return A set of edges contained in the minimum spanning tree. If there 
	 *         is not minimum spanning tree, mst will return an empty set.
	 */
	public static Set<Edge> mst(Graphs graph) {

		// First check if graph is connnected
		if (Algorithm.numberOfComponets(graph)!=1) return null;
		Set<Edge> mst = new HashSet<Edge>(); 
		int n = graph.size();
		HashSet<Integer> visited = new HashSet<Integer>(graph.size());
		int u = Algorithm.randomNode(graph);
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

	/* Returns a random node from the graph in O(1) time */
	private static Integer randomNode(Graphs graph) {
		return graph.nodes().iterator().next();
	}

	/* Helper function for testing now actual part of the API */
	// TODO move these to a utility class for testing.
	public static void printGraph(Graphs graph) {

		if (graph == null) {
			System.out.println("null pointer dude");
			return;
		}
 
		for (Integer u : graph.nodes()) {
			System.out.print(u+": ");
			for (Edge e : graph.edges(u)) {
				System.out.print(e.target()+" ");
			}
			System.out.println("\n");
		}
	}

	public static void printMap(Map<Integer, Double> distances) {
		for (Integer u : distances.keySet()) {
			System.out.print(u +": " +distances.get(u));
		}
	}

	public static void printPath(Path p) {
		for (Integer u : p.get()) {
			System.out.print(u + (u.equals(p.get(p.size()-1)) ? "\n" : " "));
		}
	}

	public static void printList(List<Integer> list) {
		for (int i=0; i<list.size(); i++)
			System.out.print(list.get(i) + (i==list.size()-1 ? "\n" : "->"));
	}
}
