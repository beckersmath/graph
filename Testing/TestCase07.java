import java.util.*;
import java.io.*;

public class TestCase07 {

	public static void outcome(Boolean answer) {

		if (answer) {
			System.out.println("Hooray!");
		} else {
			System.out.println("Fail Whale :(");
		}
	}

	public static void main(String [] args) throws Exception {
		String filename1 = "data/sample_input/topoSort.txt";
		String filename2 = "data/sample_input/stronglyConnected.txt";
		DirectedGraph g1 = DirectedGraph.makeGraph();
		DirectedGraph g2 = DirectedGraph.makeGraph();
		GraphParser gp = GraphParser.makeGraphParser();
		g1 = (DirectedGraph) gp.loadGraph(g1, filename1);
		g2 = (DirectedGraph) gp.loadGraph(g2, filename2);
		outcome(Algorithm.cyclic(g1) == false);
		outcome(Algorithm.cyclic(g2) == true);
	}
}
