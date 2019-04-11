/**
 * Your Full Name: ___________________________________________
 * <p>
 * <p>
 * Your @iu.edu Username: ____________________________________
 * <p>
 * <p>
 * Lab Section (circle one):   A    B    C    D    E    F    G
 * <p>
 * <p>
 * Final Start Time (circle one):    10:15am      12:30pm      7:15pm
 **************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * lab15: starter code.
 * <p>
 * For our purposes, a graph has no self-loops and no parallel
 * edges. We assume that vertices are labeled with a string and
 * edges are labeled with a non-negative integer.
 * <p>
 * LinkedGraph is a class that implements an undirected graph using
 * adjacency lists, where an adjacency list is an AList. Each
 * association in the AList has the incident vertex as the key and the
 * weight of the edge as the value.
 * <p>
 * TODO
 * <p>
 * (1) Copy your ListGraph.isConnected() definition from lab13 into
 * this file.
 * (2) Copy definitions of the following classes into your src
 * folder: Assoc, AList, Map, and HashMap.
 * (3) If you do not have a correct implementation of isConnected(),
 * then finish that now.
 * (4) Implement the pathLength() method.
 * (5) Challenge: Implement the shortestPath() method. (Note: you will need
 * your PriorityQueue class from p7.)
 * <p>
 * ************************
 * BEFORE THE END OF LAB *
 * ************************
 * <p>
 * Make a printout of this file. You need to do this early enough so
 * that your printout is off the presses before the end of lab.
 * <p>
 * Fill out the information at the top IN INK and give the printout to
 * your AI. Your printout will be returned to you at the final and you
 * will be able to use it during the exam.
 * <p>
 * Obviously, you're not allowed to just paste in a lot of random
 * stuff into your file before printing. It's limited to the code you
 * need to write to solve the TODOs for lab.
 */

public class LinkedGraph implements Graph {
    String name = "";
    Map<String, AList<String, Integer>> adjLists = new HashMap<>(10);
    int m; // the number of edges in this graph

    /**
     * Construct the graph with no name and with no nodes and no edges.
     */

    public LinkedGraph() {
    }

    /**
     * Construct the graph with the given name and with no nodes and no
     * edges.
     */

    public LinkedGraph(String name) {
        this.name = name;
    }

    /**
     * Adds the given vertex to this graph. Does nothing if the vertex
     * already exists.
     */

    public void addVertex(String v) {
        if (!hasVertex(v))
            adjLists.put(v, new AList<>());
    }

    /**
     * Adds the edge from u to v of the given weight to this graph. If
     * the edge already exists, then its weight is replaced with the
     * new weight. The incident vertices are automatically added.
     */

    public void addEdge(String u, String v, int weight) {
        addVertex(u);
        addVertex(v);
        AList<String, Integer> uList, vList;
        uList = adjLists.get(u);
        vList = adjLists.get(v);
        if (hasEdge(u, v)) {
            uList.get(v).value = weight;
            vList.get(u).value = weight;
        } else {
            uList.addFront(new Assoc<>(v, weight));
            vList.addFront(new Assoc<>(u, weight));
            m++;
        }
    }

    /**
     * Returns true iff the vertex v exists.
     */

    public boolean hasVertex(String v) {
        return adjLists.get(v) != null;
    }

    /**
     * Returns true iff the edge between u and v exists.
     */

    public boolean hasEdge(String u, String v) {
        return hasVertex(u) && hasVertex(v) &&
                adjLists.get(u).get(v) != null;
    }

    /**
     * Returns the weight of the edge from u to v, if it exists.
     * Throws a NoSuchElementException if the edge does not exist.
     */

    public int weight(String u, String v) {
        if (hasEdge(u, v))
            return adjLists.get(u).get(v).value;
        throw new NoSuchElementException();
    }

    /**
     * Returns the name of this graph.
     */

    public String getName() {
        return name;
    }

    /**
     * Returns the number of vertices in this graph.
     */

    public int getNumVertices() {
        return adjLists.size();
    }

    /**
     * Returns the number of edges in this graph.
     */

    public int getNumEdges() {
        return m;
    }

    /**
     * Returns a list of vertices that are adjacent to v in this graph.
     */

    public List<String> adj(String v) {
        List<String> neighbors = new DoublyLinkedList<>();
        if (hasVertex(v))
            for (Assoc<String, Integer> edge : adjLists.get(v))
                neighbors.add(edge.key);
        return neighbors;
    }

    /**
     * TODO
     * <p>
     * Returns true iff there is a path (possibly empty) from vertex u to
     * vertex v in this graph.
     */

    public boolean isConnected(String u, String v) {
        List<String> holder = new DoublyLinkedList<>();
        return isConnectedHelper(u, v, holder);
    }

    public boolean isConnectedHelper(String start, String end, List<String> path_so_far){
        if (start.equals(end)){
            return true;
        }else {
            for (String one_step : adj(start)) {
                if (!path_so_far.contains(one_step)){
                    path_so_far.add(one_step);
                    if (isConnectedHelper(one_step,end, path_so_far)){
                        return true;
                    }
                    path_so_far.remove(0);
                }
            }
        }
        return false;
    }

    /**
     * TODO
     * <p>
     * Returns the length of the given path. A path is a list of
     * vertices where adjacent vertices in the path are connected by an
     * edge in the graph.
     */

    public int pathLength(List<String> path) {
        int result = 0;
        if (path.size() == 0)
            return 0;
        if (path.size() == 1)
            return 0;
        for (int i = 1; i < path.size(); i++) {
            AList<String, Integer> neighbors = adjLists.get(path.get(i - 1));
            result += neighbors.get(path.get(i)).value;
        }
        return result;
    }

    /**
     * TODO (Challenge)
     * <p>
     * Runs Dijkstra's algorithm to determine the shortest path from
     * s to t in this graph. Assume this graph is connected and s and
     * t are legal vertices.
     */

    public List<String> shortestPath(String s, String t) {
        assert hasVertex(s);
        assert hasVertex(t);
        assert isConnected(s, t);

        class Label {
            int dist;
            String parent;

            Label(int dist, String parent) {
                this.dist = dist;
                this.parent = parent;
            }

            public String toString() {
                return String.format("[%d,%s]", dist, parent);
            }
        }

        Map<String, Label> labels = new HashMap<>();

        /**
         * TODO: the rest of the code goes here.
         */

//        int dist (String v) {
//            return labels.get(v).dist;
//        }

        labels.put(s, new Label(0, null));
        PriorityQueue<String> fringe = new PriorityQueue<>((x,y) -> labels.get(x).dist - labels.get(y).dist);
        fringe.offer(s);

        while (!fringe.isEmpty()) {
            String x = fringe.poll();
            for (String y: adj(x)) {
                int alt = labels.get(x).dist + weight(x, y);
                if (labels.get(y) == null){
                    labels.put(y, new Label(alt, x));
                    fringe.offer(y);
                }
                else if (alt < labels.get(y).dist) {
                    fringe.remove(y);
                    labels.put(y, new Label(alt, x));
                    fringe.offer(y);
                }
            }
        }

        List<String> path = new DoublyLinkedList<>();
        String curr = t;
        while (!curr.equals(s)) {
            path.addFront(curr);
            curr = labels.get(curr).parent;
        }
        path.addFront(s);
        return path;
    }

    /**
     * Returns a textual representation of this graph.
     */

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("LinkedGraph %s: n=%d, m=%d",
                getName(),
                getNumVertices(),
                getNumEdges()));
        Iterator<String> it = adjLists.keys();
        while (it.hasNext()) {
            String v = it.next();
            sb.append(String.format("\n  %s: ", v));
            sb.append(adjLists.get(v));
        }
        return sb.toString();
    }
}
