package priorityQueue;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Binary heap
 */
public class PriorityQueue {
  private ArrayList<Node> queue = new ArrayList<>();
//  private HashMap<Integer, Integer> indexes = new HashMap<>();

  public void insert(int value, int priority) {
    queue.add(new Node(value, priority));
    shiftUp(getSize() - 1);
  }

  /**
   * Returns value of the highest priority or null if the queue is empty.
   * @return Value of the highest priority.
   */
  public Node top() {
    if (isEmpty()) {
      System.out.println();
      return null;
    } else {
      Node top = queue.get(0);
      System.out.println(top);
      return top;
    }
    // TODO: heapify?
  }

  /**
   * If the queue isn't empty, returns value of the highest priority and removes it.
   * @return Value of the highest priority.
   */
  public Node pop() {
    Node popValue = top();
    if (popValue != null) {
      Collections.swap(queue, 0, getSize() - 1);
      queue.remove(getSize() - 1);
      shiftDown(0);
    }
    return popValue;
  }

  public void priority(int value, int priority) {
    // TODO: O(log(n)) complexity
    for (Node node : queue) {
      if (node.getValue() == value && node.getPriority() == priority) {
        node.setPriority(priority);
        shiftUp(queue.indexOf(node));
      }
    }
  }

  public void print() {
    System.out.println(this.toString());
  }

  public int getSize() {
    return queue.size();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  private int parent(int i) {
    return 2 * i;
  }

  private int left(int i) {
    return i / 2;
  }

  private int right(int i) {
    return (i / 2) + 1;
  }

  private void shiftUp(int index) {
    int current, parent;
    while (index >= 0) {
      current = index;

      parent = (current - 1)/2;

      if (parent >= 0 && queue.get(parent).getPriority() > queue.get(current).getPriority()) {
        current = parent;
      }

      if (current == index) return;

      swap(current, index);

      index = current;
    }
  }

  private void shiftDown(int index) {
    int current, left, right;
    int size = queue.size();
    while (index < size) {
      current = index;

      left = 2 * current + 1;
      right = left + 1;

      if (left < size && queue.get(left).getPriority() < queue.get(current).getPriority()) {
        current = left;
      }
      if (right < size && queue.get(right).getPriority() < queue.get(current).getPriority()) {
        current = right;
      }

      if (current == index) return;

      swap(current, index);

      index = current;
    }
  }

  private void swap(int i, int j) {
    Collections.swap(queue, i, j);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("[");
    for (Node node : queue) {
      result.append(node.toString());
    }
    result.append("]");
    return result.toString();
  }
}
