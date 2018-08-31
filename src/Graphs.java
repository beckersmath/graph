import java.util.Set;
import java.util.LinkedList;


// TODO refactor all methods to use insert, remove, contains
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
	public Set<Integer> nodes();
	public LinkedList<Edge> edges(int v);
	public int size();
	public int edgeTotal();
	public void addNode(int u);
	public void addEdge(int u, int v);
	public void addEdge(int u, int v, double weight);
	public void addEdge(Edge e);
	public void deleteNode(int u);
	public void deleteEdge(int u, int v);
	public boolean containsNode(int v);
	public boolean containsEdge(int u, int v);
	public Edge getEdge(int u, int v);
}