import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class MaxMatching {
    private int k;
    private int[][] capacity;
    private int[][] flow;
    private ArrayList<Integer>[] adjList;
    private int[] pi;
    private int source;
    private int sink;
    private int size;

    public MaxMatching(int k, int i) {
        this.k = k;
        size = (1 << k) * 2 + 2;
        source = 0;
        sink = size - 1;
        flow = new int[size][size];
        pi = new int[size];
        ArrayList<Integer> ints = new ArrayList<>();
        for (int j = (1 << k) + 1; j <= (1 << k) * 2; j++) {
            ints.add(j);
        }
        capacity = new int[size][size];
        adjList = new ArrayList[size];
        for (int j = 0; j < size; j++) {
            adjList[j] = new ArrayList<>();
        }
        for (int j = 1; j <= 1 << k; j++) {
            adjList[0].add(j);
            adjList[j].add(0);
            capacity[0][j] = 1;
            Collections.shuffle(ints);
            for (int l = 0; l < i; l++) {
                adjList[j].add(ints.get(l));
                adjList[ints.get(l)].add(j);
                capacity[j][ints.get(l)] = 1;
            }
            adjList[(1 << k) + j].add(sink);
            adjList[sink].add((1 << k) + j);
            capacity[(1 << k) + j][sink] = 1;
        }
    }

    private int bfs() {
        for (int j = 0; j < size; j++) {
            pi[j] = -1;
        }
        int[] M = new int[size];
        M[source] = Integer.MAX_VALUE;
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int j = 0; j < adjList[u].size(); j++) {
                int v = adjList[u].get(j);
                if (capacity[u][v] - flow[u][v] > 0 && pi[v] == -1) {
                    pi[v] = u;
                    M[v] = Math.min(M[u], capacity[u][v] - flow[u][v]);
                    if (v != sink) {
                        q.add(v);
                    } else {
                        return M[sink];
                    }
                }
            }
        }
        return 0;
    }

    public int maxMatch() {
        int f = 0;
        while (true) {
            int m = bfs();
            if (m == 0) {
                break;
            }
            f += m;
            int v = sink;
            while (v != source) {
                int u = pi[v];
                flow[u][v] += m;
                flow[v][u] -= m;
                v = u;
            }
        }
        return f;
    }

    public void glpk(String filename) {
        try {
            FileWriter writer = new FileWriter(new File(filename + ".mod"));
            PrintWriter pw = new PrintWriter(writer);
            pw.print("param n, integer, >= 2;\n" +
                    "/* number of nodes */\n" +
                    "\n" +
                    "set V, default {1..n};\n" +
                    "/* set of nodes */\n" +
                    "\n" +
                    "set E, within V cross V;\n" +
                    "/* set of arcs */\n" +
                    "\n" +
                    "param a{(i,j) in E}, > 0;\n" +
                    "/* a[i,j] is capacity of arc (i,j) */\n" +
                    "\n" +
                    "param s, symbolic, in V, default 1;\n" +
                    "/* source node */\n" +
                    "\n" +
                    "param t, symbolic, in V, != s, default n;\n" +
                    "/* sink node */\n" +
                    "\n" +
                    "var x{(i,j) in E}, >= 0, <= a[i,j];\n" +
                    "/* x[i,j] is elementary flow through arc (i,j) to be found */\n" +
                    "\n" +
                    "var flow, >= 0;\n" +
                    "/* total flow from s to t */\n" +
                    "\n" +
                    "s.t. node{i in V}:\n" +
                    "/* node[i] is conservation constraint for node i */\n" +
                    "\n" +
                    "   sum{(j,i) in E} x[j,i] + (if i = s then flow)\n" +
                    "   /* summary flow into node i through all ingoing arcs */\n" +
                    "\n" +
                    "   = /* must be equal to */\n" +
                    "\n" +
                    "   sum{(i,j) in E} x[i,j] + (if i = t then flow);\n" +
                    "   /* summary flow from node i through all outgoing arcs */\n" +
                    "\n" +
                    "maximize obj: flow;\n" +
                    "/* objective is to maximize the total flow through the network */\n" +
                    "\n" +
                    "solve;\n" +
                    "\n" +
                    "printf{1..56} \"=\"; printf \"\\n\";\n" +
                    "printf \"Maximum flow from node %s to node %s is %g\\n\\n\", s, t, flow;\n" +
                    "\n" +
                    "data;\n\n");
            pw.println();
            pw.printf("param n := %d;\n", (2 << k) + 2);
            pw.println();
            pw.println("param : E : a :=");
            for (int j = 0; j < adjList.length; j++) {
                for (Integer i : adjList[j]) {
                    pw.printf("  %d %d %d\n", j + 1, i + 1, 1);
                }
            }
            pw.println(";\n");
            pw.print("end;");
            pw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void matchTest(int reps) {
        try {
            FileWriter writerT = new FileWriter(new File("statsMatchTime.csv"));
            FileWriter writerM = new FileWriter(new File("statsMatchMatches.csv"));

            writerT.append("k;1;2;3;4;5;6;7;8;9;10;\n");
            writerM.append("k;1;2;3;4;5;6;7;8;9;10;\n");

            System.out.println("Starting tests");
            for (int k = 3; k <= 10; k++) {
                writerT.append(k + ";");
                writerM.append(k + ";");
                for (int i = 1; i <= k; i++) {
                    System.out.println("k: " + k + " i: " + i);
                    double avgMatches = 0;
                    double avgTime = 0;

                    for (int j = 0; j < reps; j++) {
                        MaxMatching maxMatch = new MaxMatching(k, i);
                        long start = System.nanoTime();
                        int matches = maxMatch.maxMatch();
                        long end = System.nanoTime();
                        double time = (end - start) / 1000000000d;

                        avgMatches += matches / (reps * 1d);
                        avgTime += time / reps;
                    }
                    writerT.append(avgTime + ";");
                    writerM.append(avgMatches + "");
                }
                writerT.append("\n");
                writerM.append("\n");
            }

            writerT.flush();
            writerM.flush();
            writerT.close();
            writerM.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        matchTest(Integer.parseInt(args[0]));
        if (args.length > 1) {
            for (int k = 1; k <= 10; k++) {
                for (int i = 1; i <= k; i++) {
                    MaxMatching maxMatching = new MaxMatching(k, i);
                    maxMatching.glpk("matchglpk" + k + "_" + i);
                }
            }
        }
    }
}
