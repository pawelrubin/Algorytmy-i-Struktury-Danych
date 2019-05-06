package algorithms;

import graph.DirectedGraph;
import graph.GraphEdge;
import priorityQueue.Node;
import priorityQueue.PriorityQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {
  private DirectedGraph graph;

  public Dijkstra(DirectedGraph graph) {
    this.graph = graph;
  }

  public List<Integer>[] shortestPath(int from) {
    int[] d = new int[graph.getVerticesCount()];
    int[] prev = new int[graph.getVerticesCount()];
    for (int i = 0; i < graph.getVerticesCount(); i++) {
      d[i] = Integer.MAX_VALUE;
      prev[i] = -1;
    }
    d[from] = 0;

    PriorityQueue q = new PriorityQueue();
    for (int i = 0; i < graph.getVerticesCount(); i++) {
      q.insert(i, d[i]);
    }

    while (!q.isEmpty()) {
      Node node = q.pop();
      int u = node.getValue();

      for (GraphEdge edge : graph.getEdges()[u]) {
        if (d[edge.getEnd()] > d[u] + edge.getWeight()) {
          d[edge.getEnd()] = d[u] = edge.getWeight();
          prev[edge.getEnd()] = u;
          q.priority(edge.getEnd(), d[edge.getEnd()]);
        }
      }
    }

    List<Integer>[] res = new List[graph.getVerticesCount()];
    for (int i = 0; i < d.length; i++) {
      res[i] = new ArrayList<>();
      int curr = i;
      while (prev[curr] != -1) {
        res[i].add(curr);
        curr = prev[curr];
      }
      res[i].add(from);
      Collections.reverse(res[i]);
    }
    return res;
  }
}
