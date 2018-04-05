import java.util.HashMap;
import java.util.Set;
import java.util.LinkedList;

/**
* Abstract graph implements the {@inheritDoc} interface, and defines most
* of the method for a graph. The abstract methods are specific to the 
* graph flavor and are implemented in subclasses.
* @author Tyler Townsend
* @version 0.1
* @since 2018-04-05
*/

public abstract class AbstractGraph implements Graphs {
	private HashMap<Node, LinkedList<Edge>> adjList;
	private Boolean directed;
	private Boolean weighted;
	private int numberOfEdges;

	protected AbstractGraph() {
		adjList = new HashMap<Node, LinkedList<Edge>>();
	}

	protected AbstractGraph(int numberOfNodes) {
		adjList = new HashMap<Node, LinkedList<Edge>>(numberOfNodes);
	}

	public Boolean directed() {
		return directed;
	}

	protected void setDirected(boolean status) {
		directed = status;
	}

	public Boolean weighted() {
		return weighted;
	}

	public void setWeighted(boolean status) {
		if (weighted == null)
			weighted = status;
	}

	public Set<Node> nodes() {
		return adjList.keySet();
	}

	public LinkedList<Edge> edges(Node v) {
		return adjList.get(v);
	}

	public int size() {
		return adjList.size();
	}

	public int edgeTotal() {
		return numberOfEdges;
	}

	protected void incrementEdges() {
		numberOfEdges++;
	}

	protected void decrementEdges() {
		numberOfEdges--;
	}

	public void addNode(int u) {
		this.addNode(Node.makeNode(u));
	}

	public void addNode(Node u) {
		if (!this.containsNode(u))
			adjList.put(u, new LinkedList<Edge>());
	}

	public void addEdge(int u, int v) {
		this.addEdge(Node.makeNode(u), Node.makeNode(v));
	}

	public void addEdge(int u, int v, double weight) {
		addEdge(Node.makeNode(u), Node.makeNode(v), weight);
	}

	public void addEdge(Edge e) {
		addEdge(e.source(), e.target(), e.weight());
	}

	public void deleteNode(int u) {
		this.deleteNode(Node.makeNode(u));
	}

	public void deleteNode(Node u) {
		for (Edge e : this.edges(u) ) {
			this.deleteEdge(u, e.target());
			decrementEdges();
		}
	}

	public void deleteEdge(int u, int v) {
		this.deleteEdge(Node.makeNode(u), Node.makeNode(v));
	}

	public boolean containsNode(Node v) {
		return adjList.containsKey(v);
	}

	public boolean containsEdge(Node u, Node v) {
		if (!this.containsNode(u)||!this.containsNode(v)) return false;
		if (getEdge(u,v) == null) return false;
		return true;
	}

	public Edge getEdge(Node u, Node v) {
		for (Edge e : this.edges(u)) {
			if (e.target().equals(v))
				return e;
		}
		return null;
	}

	/** 
	 * Abstract methods, require further implementation 
	 */
	abstract public void addEdge(Node u, Node v);
	abstract public void addEdge(Node u, Node v, double weight);
	abstract public void deleteEdge(Node u, Node v);
}
