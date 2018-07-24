/**
 * The AbstractGraph implements the {@inheritDoc} interface, and defines most
 * of the methods for a graph. The abstract methods are specific to the 
 * graph flavor and are implemented in subclasses.
 * 
 * @author Tyler Townsend
 * @version 0.1
 * @since 2018-04-05
 */

import java.util.HashMap;
import java.util.Set;
import java.util.LinkedList;


public abstract class AbstractGraph implements Graphs {
	// TODO The HashMap Will Store the node number but a second data structure
	// will store the address to the meta data
	private HashMap<Integer, LinkedList<Edge>> adjList;
	private HashMap<Integer, Node> nodeData;
	private Boolean directed;
	private Boolean weighted;
	private int numberOfEdges;

	/** 
	 * This is the constructor for instantiating the graph data structure. 
	 */
	protected AbstractGraph() {
		adjList = new HashMap<Integer, LinkedList<Edge>>();
	}

	/**
	 * This is the constructor for instantiating this graph structure with
	 * a speficied size.
	 * 
	 * @param numberOfNodes the number of vertices the graph contains.
	 */
	protected AbstractGraph(int numberOfNodes) {
		adjList = new HashMap<Integer, LinkedList<Edge>>(numberOfNodes);
	}

	/** 
	 * This method determines if this graph is a directed or un directed graph. 
	 * directed will return True if this graph is directed and False otherwise
	 * @return boolean
	 */
	public Boolean directed() {
		return directed;
	}

	/*
	 * Sets the state of the graph to be directed or undirected. This will be set
	 * by what data is loaded into this graph.
	 */
	protected void setDirected(boolean status) {
		directed = status;
	}

	/**
	 * This returns a boolean describing the edge weights of this graph. Returns
	 * True if weighted and False otherwise. 
	 * 
	 * <p> If the graph is unweighted, then the edge weights of the graph are set
	 * to a value of 1 as a default.
	 */
	public Boolean weighted() {
		return weighted;
	}

	/**
	 * Sets the state of this graph to be weighted if status is True and unweighted
	 * if otherwise. 
	 * 
	 * <p> When an instance of this graph is created, the intial value
	 * of the graphs weight is set to null. Once the weight is set then the value 
	 * cannot be changed.
	 *
	 * <p> The weight of the graph is set when the first edge is added. If the edge
	 * is weighted then the graph will be set to be weighted.
	 * @param status sets this graph to be weighted or unweighted. 
	 */
	public void setWeighted(boolean status) {
		if (weighted == null)
			weighted = status;
	}

	/** 
	 * Returns a {@code Set} of all the vertices in this graph.
	 * 
	 * @return a set of nodes
	 */
	public Set<Integer> nodes() {
		return adjList.keySet();
	}


	/**
 	 * Returns a {@code LinkedList} of edges adjacent to Node v.
	 */
	public LinkedList<Edge> edges(int v) {
		return adjList.get(v);
	}

	/**
	 * Returns a the number of nodes in this graph.
	 */
	public int size() {
		return adjList.size();
	}

	/**
    * Returns the total number of edges in this graph.
    */
	public int edgeTotal() {
		return numberOfEdges;
	}

	/*
	Increments the number of edges in this graph when a new one is added.
	 */
	protected void incrementEdges() {
		numberOfEdges++;
	}

	/*
	Decrements the number of edges when an edge is removed.
	 */
	protected void decrementEdges() {
		numberOfEdges--;
	}

	/**
	 * Inserts Node u
	 */
	public void addNode(int u) {
		if (!this.containsNode(u))
			adjList.put(u, new LinkedList<Edge>());
	}

	/**
	 * Inserts an edge between Node u and Node v with weight.
	 */
	public void addEdge(Edge e) {
		addEdge(e.source(), e.target(), e.weight());
	}

	/**
	 * Removes Node u
	 */
	public void deleteNode(int u) {
		for (Edge e : this.edges(u) ) {
			this.deleteEdge(u, e.target());
			decrementEdges();
		}
	}

	/**
	 * Returns a boolean stating if this graph contains Node v.
	 * True if Node v is in this graph and false otherwise.
	 */
	public boolean containsNode(int v) {
		return adjList.containsKey(v);
	}


	// TODO Instead of returning false for the case where any node is 
	// missing from the graph, return null or throw an exception.
	/**
	 * Returns a boolean stating if this graph contains an edge
	 * between the nodes u and v. 
	 *
	 * <p> Returns True if the edge is in the graph and false otherwise.
	 * 
	 * <p> Returns false if this graph does not contain both node u and node v.
	 */
	public boolean containsEdge(int u, int v) {
		if (!this.containsNode(u)||!this.containsNode(v)) return false;
		if (getEdge(u,v) == null) return false;
		return true;
	}

	/**
	 * Returns the edge between Node u and Node v in this graph. If there is no
	 * edge between Node u and Node v then this method returns a null pointer.
	 * 
	 * @return e is the edge between Node u and Node v
	 */
	public Edge getEdge(int u, int v) {
		for (Edge e : this.edges(u)) {
			if (e.target().equals(v))
				return e;
		}
		return null;
	}

	/*
	 * Abstract methods, require further implementation 
	 */
	abstract public void addEdge(int u, int v);
	abstract public void addEdge(int u, int v, double weight);
	abstract public void deleteEdge(int u, int v);
}
