import java.util.*;
import java.io.*;

public class TestCase06 {

	public static void outcome(Boolean answer) {

		if (answer) {
			System.out.println("Hooray!");
		} else {
			System.out.println("Fail Whale :(");
		}
	}

	public static void main(String [] args) throws Exception {
		DirectedGraph gf = DirectedGraph.makeGraph();
		GraphParser gp = GraphParser.makeGraphParser();
		gf = (DirectedGraph) gp.loadGraph(gf, "data/sample_input/topoSort.txt");
		Algorithm.printGraph(gf);
		Algorithm.printList(Algorithm.topologicalSort(gf));
	}
}
