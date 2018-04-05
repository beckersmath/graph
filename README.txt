========================================================================================
Graph Library: Introduction
========================================================================================
This library implements and adjaceny list graph representation. This is a working demo.
The Graph objects may hold roughly a million nodes and edges. The library has an
algorithms API that performs different traverals, colorings, and provides information
on paths, topological orderings, as well as connectivity.

========================================================================================
GETTING STARTED
========================================================================================
Java 8 is required for the library, so it is important to have this installed prior to
any implementations. Only the java source is provided and it needs to be compiled in 
the same directory as your main program. To compile the code, execute

    javac *.java

========================================================================================
ABOUT THE LIBRARY
========================================================================================

The library has a 2 Graph Classes:
    Graph - undirected graphs
    DirectedGraph - directed graphs.

    Summary of their methods are as follows
    
        The constructors are made with static factory method
        public static Graph.makeGraph();
        public static DirectedGraph.makeGraph();

        public Boolean directed() - returns true if graph is directed false otherwise
        public Boolean weighted() - returns true if graph is weighted false otherwise
        public Set<Node> nodes() - returns the set of all of nodes in the graph
        public LinkedList<Edge> edges(Node v) - returns the list of edges for Node v
        public int size() - returns the number of nodes
        public int edgeTotal() - returns the number of edges
        public void addNode(int u) - insert u as a node
        public void addNode(Node u) - insert node u
        public void addEdge(int u, int v) - insert u and v as edges
        public void addEdge(int u, int v, double weight) - insert u and v as edges with weight 
        public void addEdge(Node u, Node v) - insert edges with references to nodes u and v
        public void addEdge(Node u, Node v, double weight) - insert edges with references to nodes u and v with weight
        public void addEdge(Edge e) - insert Edge e
        public void deleteNode(int u) - delete the node with value u
        public void deleteNode(Node u) - delete node u
        public void deleteEdge(int u, int v) - delete edge with value u and v
        public void deleteEdge(Node u, Node v) - delete edge with nodes u and v
        public boolean containsNode(Node v) - returns true if graph contains node v
        public boolean containsEdge(Node u, Node v) return true if graph contains an edge between u and v

    To run the graph objects on graph data, use the GraphParser class
        The constructor is a public static-factory method
        public static GraphParser makeGraphParser();

        it has one public method which returns a graph interface: loadGraph(String "path/to/file/from/current/directory");
        public Graphs loadGraph(Graphs g, String filename) - returns the new graph

    public static void main (String[] args) {
        Graph graph = Graph.makeGraph();
        DirectedGraph digraph = DirectedGraph.makeGraph();
        GraphParser gp = GraphParser.makeGraphParser();
        graph = gp.loadGraph(graph, "path/to/graph-data");
        digraph = gp.loadGraph(digraph, "path/to/graph-data");
    }

Algorithms contains many algorithms to run on the graph:

    Method Summary
    public static Set<Edge> bfs(Graphs graph, int root) - returns edges in BFS tree
    public static List<Node> dfs(Graphs graph, int root) - returns nodes in DFS tree in order of traversal
    public static boolean bipartite(Graphs graph) - returns true if graph is bipartite false otherwise
    public static int numberOfComponets (Graphs graph) - returns the number of components
    public static ArrayList<List<Node>> connectedComponents(Graphs graph) - returns a  list of all the DFS trees
    public static boolean stronglyConnected(Graphs graph) - returns true if graph is strongly connected
    public static List<Node> topologicalSort(DirectedGraph graph) - returns the list of nodes in the toplogical order or null if it has none
    public static boolean dag(Graphs graph) - returns true if graph is a dag
    public static boolean cyclic(DirectedGraph graph) - returns true is the graph contains a cycle
    public static boolean containsPath(Graphs graph, int s, int t) - truens true is there is a path from s-t. 
    public static Path getPath(Graphs graph, int s, int t) BFS algorith for unweighted/ Dijkstra's for weighted to return a Path. Returns null if no path
    public static Map<Node, Double> getDistances(Graphs graph, int source) - Dijkstra's to get all the distance to each node. Returns null if disconnected
    public static Set<Edge> mst(Graphs graph) - Returns the set of Edges in the MST of graph. Returns null if disconnected
    public static double mstValue(Set<Edge> T) - Returns the minimum spanning tree weight