public interface Graph {
  void addVertex(String v);
  void addEdge(String u, String v, int weight);
  default void addEdge(String u, String v) {
    addEdge(u, v, 1);
  }
  boolean hasVertex(String v);
  boolean hasEdge(String v, String u);
  default int weight(String u, String v) {
    throw new UnsupportedOperationException();
  }
  List<String> adj(String v);
  int getNumVertices();
  int getNumEdges();
}

