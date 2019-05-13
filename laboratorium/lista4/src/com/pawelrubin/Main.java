package com.pawelrubin;

import com.pawelrubin.structures.*;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;

import java.util.Scanner;

public class Main {

    private static final double NANO_TO_SEC = 1000000000d;
    private static String treeType;

    public static void main(String[] args) {
        Tree<String> tree = null;
        try {
            GetOpt getOpt = new GetOpt(args, "t:");
            int c;
            while ((c = getOpt.getNextOption()) != -1) {
                if (c == 't') {
                    treeType = getOpt.getOptionArg();
                    switch (treeType) {
                        case "rbt": {
                            System.out.println("Simulating rbt");
                            tree = new RBTree<>();
                            break;
                        }
                        case "bst": {
                            System.out.println("Simulating bst");
                            tree = new BST<>();
                            break;
                        }
                        case "splay": {
                            System.out.println("Simulating splay");
                            tree = new SplayTree<>();
                            break;
                        }
                        default:
                            throw new IllegalArgumentException();
                    }
                } else {
                    printUsage();
                }
            }
            if (tree != null) {
                Scanner scanner = new Scanner(System.in);
                printOptions();
                while (true) {
                    String line = scanner.nextLine();
                    String[] words = line.split(" ");
                    switch (words[0]) {
                        case "insert": {
                            tree.insert(words[1]);
                            break;
                        }
                        case "delete": {
                            tree.delete(words[1]);
                            break;
                        }
                        case "search": {
                            tree.search(words[1]);
                            break;
                        }
                        case "load": {
                            if (words.length != 2) {
                                printOptions();
                                break;
                            }
                            long start = System.nanoTime();
                            tree.load("src/com/pawelrubin/data/" + words[1] + ".txt");
                            long end = System.nanoTime();
                            double duration = (end - start) / NANO_TO_SEC;
                            System.out.println(duration);
                            break;
                        }
                        case "inorder": {
                            tree.inOrder();
                            break;
                        }
                        case "clear": {
                            switch (treeType) {
                                case "rbt": {
                                    System.out.println("Simulating rbt");
                                    tree = new RBTree<>();
                                    break;
                                }
                                case "bst": {
                                    System.out.println("Simulating bst");
                                    tree = new BST<>();
                                    break;
                                }
                                case "splay": {
                                    System.out.println("Simulating splay");
                                    tree = new SplayTree<>();
                                    break;
                                }
                            }
                            break;
                        }
                        case "exit": {
                            System.exit(0);
                        }
                        default: {
                            printOptions();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            printUsage();
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("usage: ./main [-t <rbt|bst|splay>]");
    }

    private static void printOptions() {
        System.out.println("[insert s | delete s | search s | load <aspell | kjb | lotr | sample> | inorder]");
    }

    private static void demoString(Tree<String> tree) {
        tree.insert("elo");
        tree.insert("mordziu");
        tree.insert("co");
        tree.insert("tam");
        tree.insert("?");
        tree.inOrder();
    }

    private static void demoInteger(Tree<Integer> tree) {
        tree.insert(21);
        tree.insert(37);
        tree.insert(2137);
        tree.insert(69);
        tree.insert(420);
        tree.inOrder();
        tree.delete(2137);
        tree.inOrder();
    }
}
