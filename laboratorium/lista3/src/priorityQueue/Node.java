package priorityQueue;

public class Node {
  private int value;
  private int priority;

  public Node(int value, int priority) {
    this.value = value;
    this.priority = priority;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

//  @Override
//  public String toString() {
//    return "(p: " + priority + ", v: " + value + ")";
//  }
}
