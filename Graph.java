/**
* The Graph class instantiates an Undirected Graph Object extending the Abstract
* Graph class. This has the property that any edge (u,v) is the same as (v,u).
* @author Tyler Townsend
* @version 0.1
* @since 2018-04-05
*/

public class Graph extends AbstractGraph implements Graphs {

	protected Graph() {
		super();
		this.setDirected(false);
	}

	protected Graph(int numberOfNodes) {
		super(numberOfNodes);
		this.setDirected(false);
	}

	public static Graph makeGraph() {
		return new Graph();
	}

	public static Graph makeGraph(int numberOfNodes) {
		return new Graph(numberOfNodes);
	}

	@Override
	public void addEdge(Node u, Node v) {
		this.addNode(u);
		this.addNode(v);
		if (this.containsEdge(u,v)) return;
		this.edges(u).add(Edge.makeEdge(u,v));
		this.edges(v).add(Edge.makeEdge(v,u));
		super.incrementEdges();
	}

	@Override
	public void addEdge(Node u, Node v, double weight) {
		this.addNode(u);
		this.addNode(v);
		if (this.containsEdge(u, v)) return;
		this.edges(u).add(Edge.makeEdge(u,v, weight));
		this.edges(v).add(Edge.makeEdge(v,u, weight));
		super.incrementEdges();
	}

	@Override
	public void deleteEdge(Node u, Node v) {
		if (!this.containsEdge(u,v)) return;
		int index = 0;
		while (!this.edges(u).get(index).target().equals(v)) {
			index++;
		}
		this.edges(u).remove(index);
		index = 0;
		while (!this.edges(v).get(index).target().equals(u)) {
			index++;
		}
		this.edges(v).remove(index);
		super.decrementEdges();
	}
}