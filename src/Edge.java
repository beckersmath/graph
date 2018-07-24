/**
* The Edge class implements comparable to compare edge weights
* for sorting. This stores a references from the outgoing to
* incoming node.
* @author Tyler Townsend
* @version 0.1
* @since 2018-04-05
*/

class Edge implements Comparable<Edge> {
	private Integer u;
	private Integer v;
	private double weight;

	private Edge(Integer u, Integer v, double weight) {
		this(u,v);
		this.weight = weight;
	}

	private Edge(Integer u, Integer v) {
		this.u = u;
		this.v = v;
		this.weight = 1.0;
	}

	protected static Edge makeEdge(Integer u, Integer v, double weight) {
		return new Edge (u,v, weight);
	}

	protected static Edge makeEdge(Integer u, Integer v) {
		return new Edge(u, v);
	}

	public double weight() {
		return weight;
	}

	public Integer target() {
		return v;
	}

	public Integer source() {
		return u;
	}

	@Override
	public String toString() {
		return source() + " " + target() + " " + weight(); 
	}

	protected Edge reverseEdge() {
		return new Edge(v, u, weight);
	}

	public int compareTo(Edge e) {
		if (this.weight() - e.weight() > 0)
			return 1;
		else if (this.weight() - e.weight() < 0)
			return -1;
		else return 0;
	}

}