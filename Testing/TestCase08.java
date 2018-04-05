import java.util.*;
import java.io.*;

public class TestCase08 {

	public static void outcome(Boolean answer) {

		if (answer) {
			System.out.println("Hooray!");
		} else {
			System.out.println("Fail Whale :(");
		}
	}

	public static void printList(ArrayList<Node> list) {
		for (int i=0; i<list.size(); i++)
			System.out.print(list.get(i) + (i==list.size()-1 ? "\n" : "->"));
	}

	public static void main(String [] args) throws Exception {
		DirectedGraph g1 = DirectedGraph.makeGraph();
		g1.addEdge(0,1,5);
		g1.addEdge(0,8,1);
		g1.addEdge(0,6,9);
		g1.addEdge(0,7,9);
		g1.addEdge(1,2,7);
		g1.addEdge(2,3,8);
		g1.addEdge(2,4,11);
		g1.addEdge(2,8,6);
		g1.addEdge(3,4,20);
		g1.addEdge(4,8,2);
		g1.addEdge(4,5,4);
		g1.addEdge(5,6,1);
		g1.addEdge(6,8,3);
		g1.addEdge(6,7,2);
		Path path1 = Algorithm.shortestPath(g1, 0, 5);
		Algorithm.printPath(path1);
		Map<Node, Double> distances = Algorithm.getDistances(g1, 0);
		for (Node u : distances.keySet()) {
			System.out.println(u + " "+ distances.get(u));
		}
		Path path2 = Algorithm.shortestPath(g1, 0, 5);
		Algorithm.printPath(path2);
		DirectedGraph g2 = DirectedGraph.makeGraph();
		g2.addEdge(0, 1, 7);
		g2.addEdge(0, 3, 4);
		g2.addEdge(0, 4, 1);
		g2.addEdge(1, 2, 9);
		g2.addEdge(1, 4, 3);
		g2.addEdge(2, 3, 2);
		g2.addEdge(2, 4, 4);
		g2.addEdge(3, 4, 5);
		Set<Edge> t= Algorithm.mst(g2);
		System.out.println(Algorithm.mstValue(t));
	}
}
