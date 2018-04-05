import java.util.*;
import java.io.*;
import java.lang.instrument.Instrumentation;
import java.math.BigInteger;

public class TestCase09 {
	final static int facebookNodes = 4039;
	final static int facebookEdges = 88234;
	final static int googleNodes = 107614;
	final static int googleEdges = 13673453;
	final static int youtubeNodes = 1134890;
	final static int youtubeEdges = 2987624;
	private static Instrumentation instrumentation;

	public static void outcome(Boolean answer) {
		if (answer) {
			System.out.println("Hooray!");
		} else {
			System.out.println("Fail Whale :(");
		}
	}

	public static void main(String [] args) throws Exception {

		String filename1 = "data/ego/facebook_combined.txt";
		String filename2 = "data/ego/gplus_combined.txt";
		String filename3 = "data/com-youtube/com-youtube.ungraph.txt";
		Graph gf = Graph.makeGraph();
		GraphParser gp = GraphParser.makeGraphParser();
		gf = (Graph) gp.loadGraph(gf, filename1);

		boolean check = true;
		check &= (gf.size() == facebookNodes);
		check &= (gf.edgeTotal() == facebookEdges);
		outcome(check);

		// DirectedGraph gg = DirectedGraph.makeGraph(googleNodes);
		// gg = (DirectedGraph) gp.loadGraph(gg, filename2);
		// check &= (gf.size() == googleNodes);
		// check &= (gf.edgeTotal() == googleEdges);
		// outcome(check);

		Graph gy = Graph.makeGraph(youtubeNodes);
		gy = (Graph) gp.loadGraph(gy, filename3);
		check &= (gy.size() == youtubeNodes);
		check &= (gy.edgeTotal() == youtubeEdges);
		outcome(check);
		outcome (Algorithm.bfs(gy, 1).size() == youtubeNodes - 1);
	}

	public static int sizeof(Object obj) throws IOException {

	    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);

	    objectOutputStream.writeObject(obj);
	    objectOutputStream.flush();
	    objectOutputStream.close();

	    return byteOutputStream.toByteArray().length;
	}
}
