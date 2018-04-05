import java.util.*;
import java.io.*;

public class TestCase04 {

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
		g1 = (Graph)gp.loadGraph(g1, "data/sample_input/noPath.txt");
		outcome(Algorithm.containsPath(g1, 1,8)==true);
		outcome(Algorithm.containsPath(g1, 8,1)==true);
		outcome(Algorithm.containsPath(g1, 1,10)==false);
		Algorithm.printPath(Algorithm.getPath(g1, 1, 8));
	}
}
