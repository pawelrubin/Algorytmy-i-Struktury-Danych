package graph;

public class GraphEdge implements Comparable<GraphEdge> {
  private int start, end, weight;

  public GraphEdge(int start, int end, int weight) {
    this.start = start;
    this.end = end;
    this.weight = weight;
  }

  public int getEnd() {
    return end;
  }

  public int getStart() {
    return start;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    return String.format("[%d -> %d w: %d]", start, end, weight);
  }


  @Override
  public int compareTo(GraphEdge o) {
    return this.weight - o.weight;
  }
}
