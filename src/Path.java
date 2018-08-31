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

	/* Properties of this Path */
	private static double distance;
	private static Integer _source;
	private static List<Integer> nodes;

	private Path(Integer source) {
		nodes = new LinkedList<Integer>();
		_source = source;
		distance = 0.0;
	}

	/**
	 * Creates an instance of Path
	 * @param  source The starting point of Path
	 * @return        Returns the new instance of Path					       
	 */
	protected static Path makePath(Integer source) {
		return new Path(source);
	}

	/**
	 * Returns a List of the nodes in this Path
	 * @return Returns a List of the nodes in this Path
	 */
	public List<Integer> get() {
		return nodes;
	}

	/**
	 * Returns the specific node in Path at index
	 * @param  index 
	 * @return       [description]
	 */
	public Integer get(int node) {
		return this.get().get(node);
	}

	/**
	 * Returns the distance of this Path
	 * @return 		The distance of this Path
	 */
	public double cost() {
		return distance;
	}

	/**
	 * Returns the number of nodes in this Path
	 * @return 	The number of nodes in this Path
	 */
	public int size() {
		return this.get().size();
	}

	/*
	 Inserts a node into this source
	 */
	protected void insert(Integer source) {
		this.get().add(source);
	}

	/*
	 Inserts the edge weight from taking the last node in
	 nodes to target of e
	 */
	protected void add(Edge e) {
		this.get().add(e.target());
		distance+=e.weight();
	}
}