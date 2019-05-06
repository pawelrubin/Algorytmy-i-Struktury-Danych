package demos;

import algorithms.Kruskal;
import algorithms.Prim;
import graph.DirectedGraph;
import graph.GraphEdge;
import graph.UndirectedGraph;

import java.util.Scanner;

public class MSTDemo {

  private static UndirectedGraph graph;
  private static Scanner scanner = new Scanner(System.in);
  private static int type;

  public static void main(String[] args) {
    handleInput();
    printResult();
  }

  private static void handleInput() {
    System.out.println("Choose MST type. (1 - Kruskal, 2 - Prim)");
    type = scanner.nextInt();

    System.out.print("\nn: ");
    int n = scanner.nextInt();
    graph = new UndirectedGraph(n);
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
  }

  private static void printResult() {
    UndirectedGraph result;
    switch (type) {
      case 1: {
        Kruskal kruskal = new Kruskal(graph);
        result = kruskal.mst();
        break;
      }
      case 2: {
        Prim prim = new Prim(graph);
        result = prim.mst();
        break;
      }
      default:
        throw new IllegalStateException("Unexpected value: " + type);
    }
    System.out.println(result.toString());
    System.out.println("Sum of weights: " + result.weightSum());
  }

}
