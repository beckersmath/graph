import java.util.*;
import java.io.*;

public class TestCase1 {

	public static void main(String [] args) throws Exception {
		Scanner sc = new Scanner(new File("data/sample_input/test1.txt"));
		// Scanner sc = new Scanner(new File("data/sample_input/test1.in"));
		Graph g1 = new Graph();
		int numNodes = sc.nextInt();
		int cases;
		for (int i=1; i<=numNodes; i++) {
			cases = sc.nextInt();
			for (int j=0; j<cases; j++) {
				g1.addEdge(i,sc.nextInt());
			}
		}
		System.out.println("Graph g1");
		g1.printGraph();
		System.out.println();
		System.out.println("Tree t1");
		Tree t1 = Algorithm.bfs(g1, 1);
		t1.printGraph();
		System.out.println();
		System.out.println("Tree t2");
		Tree t2 = Algorithm.dfs(g1, 1);
		t2.printGraph();
	}
}
