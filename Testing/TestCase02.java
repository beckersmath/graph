import java.util.*;
import java.io.*;

public class TestCase02 {

	public static void main(String [] args) throws Exception {
		Scanner sc = new Scanner(new File("data/sample_input/bipartite.txt"));
		Graph g1 = new Graph();
		int numNodes = sc.nextInt();
		for (int i=0; i<numNodes; i++) {
			for (int j=0; j<numNodes; j++) {
				int val = sc.nextInt();
				if (val !=0) {
					g1.addEdge(i,val);
				}
			}
		}
		System.out.println(Algorithm.bipartite(g1));
	}
}
