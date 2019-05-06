package graph;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UndirectedGraph {
  private List<GraphEdge>[] edges;

  public UndirectedGraph(int numOfVertices) {
    this.edges = new ArrayList[numOfVertices];
    for (int i = 0; i < numOfVertices; i++) edges[i] = new ArrayList<>();
  }

  public void addEdge(GraphEdge edge) {
    edges[edge.getStart()].add(edge);
    edges[edge.getEnd()].add(new GraphEdge(edge.getEnd(), edge.getStart(), edge.getWeight()));
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

  public int weightSum() {
    int sum = 0;
    for (List<GraphEdge> path: edges) {
      for (GraphEdge edge : path) {
        sum += edge.getWeight();
      }
    }
    return sum;
  }
}
