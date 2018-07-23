import java.util.LinkedList;
import java.util.List;

/**
* Path stores the order of nodes visited along some path
* along with the weight (or hops if unweighed).
* @author Tyler Townsend
* @version 0.1
* @since 2018-04-05
*/

class Path {
	private static double distance;
	private static Node _source;
	private static List<Node> nodes;

	private Path(Node source) {
		nodes = new LinkedList<Node>();
		_source = source;
		distance = 0.0;
	}

	protected static Path makePath(Node source) {
		return new Path(source);
	}

	public List<Node> get() {
		return nodes;
	}
	public Node get(int index) {
		return this.get().get(index);
	}

	public double cost() {
		return distance;
	}

	public int size() {
		return this.get().size();
	}

	protected void insert(Node source) {
		this.get().add(source);
	}

	protected void add(Edge e) {
		this.get().add(e.target());
		distance+=e.weight();
	}
}