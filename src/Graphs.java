import java.util.Set;
import java.util.LinkedList;

/**
* The Graphs interface denots all the public methods that can be used within
* the library. The library contians implementations of weighted/unwieghted 
* directed or undirected graphs.
* @author Tyler Townsend
* @version 0.1
* @since 2018-04-05
*/

interface Graphs {
	public Boolean directed();
	public Boolean weighted();
	public Set<Node> nodes();
	public LinkedList<Edge> edges(Node v);
	public int size();
	public int edgeTotal();
	public void addNode(int u);
	public void addNode(Node u);
	public void addEdge(int u, int v);
	public void addEdge(int u, int v, double weight);
	public void addEdge(Node u, Node v);
	public void addEdge(Node u, Node v, double weight);
	public void addEdge(Edge e);
	public void deleteNode(int u);
	public void deleteNode(Node u);
	public void deleteEdge(int u, int v);
	public void deleteEdge(Node u, Node v);
	public boolean containsNode(Node v);
	public boolean containsEdge(Node u, Node v);
	public Edge getEdge(Node u, Node v);
}