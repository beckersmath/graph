import java.math.BigInteger;

/**
* Node stores the id of the graph vertex. Node will eventually
* contains meta-data of the vertex-id. 
* @author Tyler Townsend
* @version 0.1
* @since 2018-04-05
*/

class Node implements Comparable<Node> {

	private BigInteger _id;

	Node (String id) {
		_id = new BigInteger(id);
	}

	protected static Node makeNode(int id) {
		return new Node(Integer.toString(id));
	}

	protected static Node makeNode(String id) {
		return new Node(id);
	}

	public BigInteger value() {
		return _id;
	}

	public int compareTo(Node v) {
		return _id.compareTo(v.value());
	}

	@Override
	public String toString() {
		return _id.toString();
	}

	// Very cheeky quick solution
	public int hashCode() {
		return this.value().hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof Node) {
			Node h = (Node)o;
			return _id.equals(h.value());
		}
		return false;
	}
}