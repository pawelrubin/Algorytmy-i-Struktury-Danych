package algorithms;

import graph.GraphEdge;
import graph.UndirectedGraph;
import priorityQueue.PriorityQueue;

import java.util.ArrayList;
import java.util.List;

public class Kruskal {
  private int v;
  private int e;
  private List<GraphEdge> edges = new ArrayList<>();;

  public Kruskal(UndirectedGraph graph) {
    this.v = graph.getEdges().length;
    //creating a simpler graph representation
    for (List<GraphEdge> l : graph.getEdges()) {
      for (GraphEdge e : l) {
        this.edges.add(new GraphEdge(e.getStart(), e.getEnd(), e.getWeight()));
        for (GraphEdge ee : graph.getEdges()[e.getEnd()]) {
          if (ee.getEnd() == e.getStart()) {
            graph.getEdges()[e.getEnd()].remove(ee);
            break;
          }
        }
      }
    }
  }

  public UndirectedGraph mst() {
    UndirectedGraph g = new UndirectedGraph(v);

    GraphEdge[] res = new GraphEdge[v];
    int e = 0, i;
    for (i = 0; i < v; i++) res[i] = new GraphEdge(-1, -1, -1);

    edges.sort(GraphEdge::compareTo);

    subset[] subsets = new subset[v];
    for (i = 0; i < v; i++) {
      subsets[i] = new subset();
      subsets[i].parent = i;
      subsets[i].rank = 0;
    }

    i = 0;

    while (e < v - 1) {
      GraphEdge nextEdge = edges.get(i++);
      int x = find(subsets, nextEdge.getStart());
      int y = find(subsets, nextEdge.getEnd());

      if (x != y) {
        res[e++] = nextEdge;
        union(subsets, x, y);
      }
    }

    for (i = 0; i < e; ++i) {
      g.addEdge(new GraphEdge(res[i].getStart(), res[i].getEnd(), res[i].getWeight()));
    }

    return g;
  }

  private int find(subset[] subsets, int i) {
    if (subsets[i].parent != i) subsets[i].parent = find(subsets, subsets[i].parent);
    return subsets[i].parent;
  }

  private void union(subset[] subsets, int x, int y) {
    int xRoot = find(subsets, x);
    int yRoot = find(subsets, y);

    if (subsets[xRoot].rank < subsets[yRoot].rank) subsets[xRoot].parent = yRoot;
    else if (subsets[xRoot].rank > subsets[yRoot].rank) subsets[yRoot].parent = xRoot;
    else {
      subsets[yRoot].parent = xRoot;
      subsets[xRoot].rank++;
    }
  }

  private class subset {
    int parent, rank;
  }
}
