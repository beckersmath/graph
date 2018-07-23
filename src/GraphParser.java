import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
* The Graph Parser Class is instantiated to reader a file contianing preivous
* graph information. This class must be instantiated and has one public method
* {@link loadGraph()}. 
* @author Tyler Townsend
* @version 0.1
* @since 2018-04-05
*/

public class GraphParser {

	private FileInputStream inputStream;
	private BufferedReader br;
	private FileReader fr;

	private GraphParser() {
		br = null;
		fr = null;
	}

	public static GraphParser makeGraphParser() {
		return new GraphParser();
	}

	public Graphs loadGraph(Graphs graph, String filename) {
		return parse(graph, filename);
	}

	private Graphs parse(Graphs graph, String filename) {

		try ( BufferedReader br = new BufferedReader(new FileReader(filename)) ){
			inputStream = new FileInputStream(filename);
			System.out.println("Total file size to read (in bytes) :"+inputStream.available());
			return getGraph(graph, br);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream!=null) inputStream.close();
				if (br != null) br.close();
				if (fr != null) fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	private Graphs getGraph(Graphs graph, BufferedReader br) {
		// check for comments
		String temp;
		try {
			temp=br.readLine();
			while(temp!=null && isComment(temp)){
				temp = br.readLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		switch(getFileType(temp)) {
			case 0 : {
				return null;	
			}
			case 1 : {
				graph = graphType(graph , false);
				int numNodes = Integer.parseInt(temp);
				// check next line
				String secondline;
				try {
					if ((secondline=br.readLine()) == null) return null;
					boolean type = matrixRepresentation(numNodes, secondline);
					if (type == true) {
						return matrix(graph, numNodes, secondline, br);
					} else if (type == false) {
						return list(graph, numNodes, secondline, br);
					} else return null;
				} catch (IOException ex) {
					ex.printStackTrace();
				} 
			}

			case 2 : {
				String [] edges = temp.split("\\s+");
				graph = graphType(graph, false);
				graph.addEdge( Node.makeNode(edges[0]), Node.makeNode(edges[1]));
				try {
					int i = 0;
					while((temp = br.readLine()) != null) {
						edges = temp.split("\\s+");
						// if (i % 1000==0) System.out.println("Line " + i);
						graph.addEdge(Node.makeNode(edges[0]), Node.makeNode(edges[1]));
						i++;
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				return graph;
			}

			case 3 : {
				String [] edges = temp.split("\\s+");
				graph = graphType(graph, true);
				graph.addEdge(Node.makeNode(edges[0]), Node.makeNode(edges[1]), Integer.parseInt(edges[2]));
				try {
					while((temp=br.readLine()) != null) {
						edges = temp.split("\\s+");
						graph.addEdge(Node.makeNode(edges[0]), Node.makeNode(edges[1]), Integer.parseInt(edges[2]));
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				return graph;
			}
		}
		return null;
	}

	private Graphs graphType(Graphs graph, boolean weighted) {
		if (graph.directed()) {
			DirectedGraph g = DirectedGraph.makeGraph();
			g.setWeighted(weighted);
			return g;
		} else {
			Graph g = Graph.makeGraph();
			g.setWeighted(weighted);
			return g;
		}
	}

	private boolean isComment(String fileline) {
		if (fileline.charAt(0)=='#') return true;
		else if (fileline.length()>1 && fileline.substring(0,2).equals("//"))
			return true;
		else return false;
	}

	// Assuming this reprsentation is unweighted
	private boolean matrixRepresentation(int V, String secondline) {
		String [] parsed = secondline.split("\\s+");
		return (Integer.parseInt(parsed[0])!= parsed.length - 1);
	}

	private Graphs matrix(Graphs graph, int n, String secondline, BufferedReader br) {
		String [] edges = secondline.split("\\s+");
		for (int j = 0; j < n; j++) {
			int item = Integer.parseInt(edges[j]);
			if (item  == 1) graph.addEdge(1, item);
		}

		try {
			for (int i=2; i<n; i++) {
				edges = br.readLine().split("\\s+");
				for (int j=0; j<n; j++) {
					graph.addEdge(i,Integer.parseInt(edges[j]));
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return graph;
	}

	private Graphs list(Graphs graph, int n, String secondline, BufferedReader br) {

		String [] edges = secondline.split("\\s+");
		graph.addNode(1);
		for (int j=1; j<Integer.parseInt(edges[0]); j++) {
			graph.addEdge(1, Integer.parseInt(edges[j]));
		}
		try {
			for (int i = 2; i <=n; i++) {
				edges = br.readLine().split("\\s+");
				for (int j=1; j<=Integer.parseInt(edges[0]); j++) {
					graph.addEdge(i, Integer.parseInt(edges[j]));
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return graph;
	}

	private static int getFileType(String firstLine) {

		String [] parsed = firstLine.split("\\s+");
		if (parsed == null) return 0;
		if (parsed.length == 1 && GraphParser.isNumeric(parsed[0])) return 1;
		if (parsed.length == 2) return 2;
		if (parsed.length == 3) return 3;
		return 0;

	}

	private static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}
