/**
* The DirectedGraph class instantiates an di-Graph Object extending the Abstract
* Graph class. The DirectedGraph class has the property that the edge (u,v) is 
* distinct from (v, u) in the reprsentation. 
* @author Tyler Townsend
* @version 0.1
* @since 2018-04-05
*/
public class DirectedGraph extends AbstractGraph implements Graphs {

	protected DirectedGraph() {
		super();
		this.setDirected(true);
	}

	protected DirectedGraph(int numberOfNodes) {
		super(numberOfNodes);
		this.setDirected(false);
	}


	public static DirectedGraph makeGraph() {
		return new DirectedGraph();
	}

	public static DirectedGraph makeGraph(int numberOfNodes) {
		return new DirectedGraph(numberOfNodes);
	}

	@Override
	public void addEdge(int u, int v) {
		this.addNode(u);
		this.addNode(v);
		if (this.containsEdge(u,v)) return;
		this.edges(u).add(Edge.makeEdge(u,v));
		super.incrementEdges();
	}

	@Override
	public void addEdge(int u, int v, double weight) {
		this.addNode(u);
		this.addNode(v);
		if (this.containsEdge(u,v)) return;
		this.edges(u).add(Edge.makeEdge(u,v, weight));
		super.incrementEdges();
	}

	@Override
	public void deleteEdge(int u, int v) {
		if (!this.containsEdge(u,v)) return;

		int index = 0;
		while (!this.edges(u).get(index).equals(v)) {
			index++;
		}
		this.edges(u).remove(index);
		super.decrementEdges();
	}
}