import java.util.*;
import java.io.*;

public class TestCase03 {

	public static void outcome(Boolean answer) {

		if (answer) {
			System.out.println("Hooray!");
		} else {
			System.out.println("Fail Whale :(");
		}
	}

	public static void main(String [] args) throws Exception {
		Graph g1 = Graph.makeGraph();
		GraphParser gp = GraphParser.makeGraphParser();
		g1 = (Graph)gp.loadGraph(g1, "data/sample_input/tree.txt");
		Graph g2 = Graph.makeGraph();
		g2 = (Graph)gp.loadGraph(g2, "data/sample_input/cycle.txt");
		Algorithm.printGraph(g1);
		Algorithm.printGraph(g2);
		outcome(Algorithm.isTree(g1)==true);
		outcome(Algorithm.isTree(g2)==false);
	}
}
