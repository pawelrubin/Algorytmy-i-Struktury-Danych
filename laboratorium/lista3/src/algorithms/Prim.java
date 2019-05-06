package algorithms;

import graph.GraphEdge;
import graph.UndirectedGraph;
import priorityQueue.PriorityQueue;

public class Prim {
  private UndirectedGraph graph;

  public Prim(UndirectedGraph graph) {
    this.graph = graph;
  }

  public UndirectedGraph mst() {
    UndirectedGraph g = new UndirectedGraph(graph.getEdges().length);

    boolean[] mst = new boolean[graph.getEdges().length];
    int[] key = new int[graph.getEdges().length];
    int[] parent = new int[graph.getEdges().length];

    for (int i = 0; i < graph.getEdges().length; i++) {
      mst[i] = false;
      key[i] = Integer.MAX_VALUE;
      parent[i] = -1;
    }

    mst[0] = true;
    key[0] = 0;

    PriorityQueue q = new PriorityQueue();

    for (int i = 0; i < graph.getEdges().length; i++) q.insert(i, key[i]);

    while (!q.isEmpty()) {
      int vertex = q.pop().getValue();
      mst[vertex] = true;
      for (GraphEdge edge : graph.getEdges()[vertex]) {
        if (!mst[edge.getEnd()]) {
          if (key[edge.getEnd()] > edge.getWeight()) {
            q.priority(edge.getEnd(), edge.getWeight());
            parent[edge.getEnd()] = vertex;
          }
        }
      }
    }

    for (int i = 1; i < graph.getEdges().length; i++) {
      int weight = -1;
      for (GraphEdge e : graph.getEdges()[parent[i]]) {
        if (e.getEnd() == i) {
          weight = e.getWeight();
          break;
        }
      }
      g.addEdge(new GraphEdge(i, parent[i], weight));
    }

    return g;
  }
}
