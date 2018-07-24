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
	private static Integer _source;
	private static List<Integer> nodes;

	private Path(Integer source) {
		nodes = new LinkedList<Integer>();
		_source = source;
		distance = 0.0;
	}

	protected static Path makePath(Integer source) {
		return new Path(source);
	}

	public List<Integer> get() {
		return nodes;
	}
	public Integer get(int index) {
		return this.get().get(index);
	}

	public double cost() {
		return distance;
	}

	public int size() {
		return this.get().size();
	}

	protected void insert(Integer source) {
		this.get().add(source);
	}

	protected void add(Edge e) {
		this.get().add(e.target());
		distance+=e.weight();
	}
}