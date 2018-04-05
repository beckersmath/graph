import java.util.*;
import java.io.*;

public class TestCase05 {

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
		GraphParser gp = GraphParser.makeGraphParser();
		g1 = (DirectedGraph) gp.loadGraph(g1, "data/sample_input/stronglyConnected.txt");
		outcome(Algorithm.strongConnected(g1) == true);
	}
}
