package demos;

import algorithms.Dijkstra;
import graph.DirectedGraph;
import graph.GraphEdge;

import java.util.List;
import java.util.Scanner;

public class DijkstraDemo {

  private static Dijkstra dijkstra;
  private static DirectedGraph graph;
  private static Scanner scanner = new Scanner(System.in);

  private static int start;

  public static void main(String[] args) {
      handleInput();
      print();
  }

  private static void handleInput() {
    System.out.print("\nn: ");
    int n = scanner.nextInt();
    graph = new DirectedGraph(n);
    dijkstra = new Dijkstra(graph);
    System.out.print("\nm: ");
    int m = scanner.nextInt();

    for (int i = 0; i < m; i++) {
      System.out.print("\nu: ");
      int u = scanner.nextInt();
      System.out.print("v: ");
      int v = scanner.nextInt();
      System.out.print("w: ");
      int w = scanner.nextInt();
      graph.addEdge(new GraphEdge(u, v, w));
    }

    System.out.print("\nStart vertex: ");
    start = scanner.nextInt();
  }

  private static void print() {
    List<Integer>[] paths = dijkstra.shortestPath(start);
    for (int i = 0; i < paths.length; i++) {
      System.out.println(i + ": " + paths[i]);
    }
  }
}
