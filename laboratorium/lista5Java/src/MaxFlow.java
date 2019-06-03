import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MaxFlow {
    private int k;
    private int[][] capacity;
    private int[][] flow;
    private int[] pi;
    private Random rand;
    private int paths = 0;
    private int maxFlow = 0;

    public int getPaths() {
        return paths;
    }

    public int getMaxFlow() {
        return maxFlow;
    }

    public MaxFlow(int k) {
        this.k = k;
        capacity = new int[1 << k][k];
        rand = new Random();

        for (int i = 0; i < (1 << k); i++) {
            for (int j = 0; j < k; j++) {
                capacity[i][j] = ((i & (1 << j)) == 0) ? randomFlow(i, j) : 0;
            }
        }

        flow = new int[1 << k][k];
        pi = new int[1 << k];
    }

    private int maxHZ(int n) {
        n = n - ((n >> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >> 2) & 0x33333333);
        n = (((n + (n >> 4)) & 0x0f0f0f0f) * 0x01010101) >> 24;
        return Math.max(n, k - n);
    }

    private int randomFlow(int number, int index) {
        return rand.nextInt(1 << (Math.max(maxHZ(number), maxHZ(number + 1 << index)))) + 1;
    }

    private int bfs() {
        for (int i = 0; i < (1 << k); i++) {
            pi[i] = -1;
        }
        pi[0] = -2;
        int[] M = new int[1 << k];
        M[0] = Integer.MAX_VALUE;
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v = 0; v < k; v++) {
                int p = (u & (1 << v)) == 0 ? u + (1 << v) : u - (1 << v);
                if (capacity[u][v] > flow[u][v] && pi[p] == -1) {
                    pi[p] = u;
                    M[p] = Math.min(M[u], capacity[u][v] - flow[u][v]);
                    if (p != ((1 << k) - 1)) {
                        q.add(p);
                    } else {
                        return M[(1 << k) - 1];
                    }
                }
            }
        }
        return 0;
    }

    public int edmondsKarp() {
        while (true) {
            int m = bfs();
            if (m == 0) {
                break;
            }
            paths++;
            maxFlow += m;
            int v = (1 << k) - 1;
            while (v != 0) {
                int u = pi[v];
                int diff = binlog(u ^ v);
                flow[u][diff] += m;
                flow[v][diff] -= m;
                v = u;
            }
        }
        return maxFlow;
    }

    private int binlog(int bits) {
        int log = 0;
        if ((bits & 0xffff0000) != 0) {
            bits >>>= 16;
            log = 16;
        }
        if (bits >= 256) {
            bits >>>= 8;
            log += 8;
        }
        if (bits >= 16) {
            bits >>>= 4;
            log += 4;
        }
        if (bits >= 4) {
            bits >>>= 2;
            log += 2;
        }
        return log + (bits >>> 1);
    }

    public void glpk(String filename) {
        try {
            FileWriter fileWriter = new FileWriter(new File(filename + ".mod"));
            PrintWriter pw = new PrintWriter(fileWriter);
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
                    "data;\n");
            pw.println();
            pw.printf("param n := %d;\n", 1 << k);
            pw.println();
            pw.println("param : E : a :=");
            for (int i = 0; i < (1 << k); i++) {
                for (int j = 0; j < k; j++) {
                    if (capacity[i][j] != 0) {
                        int s = i + (1 << j) + 1;
                        pw.printf("  %d %d %d\n", i + 1, s, capacity[i][j]);
                    }
                }
            }
            pw.println(";\n");
            pw.print("end;");
            pw.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void flowTest(int reps) {
        try {
            FileWriter writer = new FileWriter(new File("statsFlow.csv"));

            writer.append("i;flow;paths;time;\n");

            System.out.println("Starting tests");
            for (int i = 1; i <= 16; i++) {
                System.out.println(i);
                double avgFlow = 0;
                double avgPaths = 0;
                double avgTime = 0;

                for (int j = 0; j < reps; j++) {
                    MaxFlow maxFlow = new MaxFlow(i);
                    long start = System.nanoTime();
                    maxFlow.edmondsKarp();
                    long end = System.nanoTime();
                    double time = (end - start) / 1000000000d;

                    avgFlow += maxFlow.getMaxFlow() / (reps * 1d);
                    avgPaths += maxFlow.getPaths() / (reps * 1d);
                    avgTime += time / reps;
                }
                writer.append(i + ";" + avgFlow + ";" + avgPaths + ";" + avgTime + "\n");
            }

            writer.flush();
            writer.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        flowTest(Integer.parseInt(args[0]));
        if (args.length > 1) {
            for (int i = 1; i <= 16; i++) {
                MaxFlow maxFlow = new MaxFlow(i);
                maxFlow.glpk("glpk" + i);
            }
        }
    }
}
