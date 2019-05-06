package graph;

import priorityQueue.Node;
import priorityQueue.PriorityQueue;

import java.util.ArrayList;
import java.util.List;


public class DirectedGraph {
  private List<GraphEdge>[] edges;

  public DirectedGraph(int numOfVertices) {
    edges = new List[numOfVertices];
    for (int i = 0; i < numOfVertices; i++) this.edges[i] = new ArrayList<>();
  }

  public void addEdge(GraphEdge edge) {
    edges[edge.getStart()].add(edge);
  }
  public int getVerticesCount() {
    return edges.length;
  }

  public List<GraphEdge>[] getEdges() {
    return edges;
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < edges.length; i++) {
      b.append(i);
      b.append(": ");
      for (GraphEdge e : edges[i]) {
        b.append(e);
        b.append(" ");
      }
      b.append("\n");
    }
    return b.toString();
  }
}
