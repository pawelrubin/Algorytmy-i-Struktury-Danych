package demos;

import priorityQueue.PriorityQueue;

import java.util.Scanner;

public class PriorityDemo {

  private static PriorityQueue queue = new PriorityQueue();
  private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    int m;

    System.out.print("m: ");
    m = scanner.nextInt();

    for (int i = 0; i < m; i++) {
      printOptions();
      handleChoice();
    }
  }

  private static void printOptions() {
    System.out.println("1. insert x p");
    System.out.println("2. empty");
    System.out.println("3. top");
    System.out.println("4. pop");
    System.out.println("5. priority x p");
    System.out.println("6. print");
    System.out.println("Choose 1-6.");
  }

  private static void handleChoice() {
    int choice = scanner.nextInt();
    switch (choice) {
      case 1: {
        int[] xp = xp();
        queue.insert(xp[0], xp[1]);
        break;
      }
      case 2: queue.isEmpty(); break;
      case 3: queue.top(); break;
      case 4: queue.pop(); break;
      case 5: {
        int[] xp = xp();
        queue.priority(xp[0], xp[1]);
        break;
      }

      case 6: queue.print(); break;
    }
  }

  private static int[] xp() {
    int[] result = new int[2];
    System.out.print("x: ");
    result[0] = scanner.nextInt();
    System.out.print("p: ");
    result[1] = scanner.nextInt();
    System.out.println();
    return result;
  }
}

