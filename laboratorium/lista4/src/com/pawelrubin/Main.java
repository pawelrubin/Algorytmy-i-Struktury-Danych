package com.pawelrubin;

import com.pawelrubin.structures.*;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants.S;
import static jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants.STR;

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
                        case "unload": {
                            if (words.length != 2) {
                                printOptions();
                                break;
                            }
                            long start = System.nanoTime();
                            tree.unload("src/com/pawelrubin/data/" + words[1] + ".txt");
                            long end = System.nanoTime();
                            double duration = (end - start) / NANO_TO_SEC;
                            System.out.println(duration);
                            break;
                        }
                        case "loadSearch": {
                            if (words.length != 2) {
                                printOptions();
                                break;
                            }
                            long start = System.nanoTime();
                            tree.searchLoaded("src/com/pawelrubin/data/" + words[1] + ".txt");
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
                            tree = clearTree(treeType);
                            break;
                        }
                        case "exit": {
                            System.exit(0);
                        }
                        case "test": {
                            if (words.length != 2) {
                                printOptions();
                                break;
                            }
                            tree = clearTree(treeType);
                            runTests(tree, Integer.valueOf(words[1]));
                            break;
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
        System.out.println("[insert s | deleteNode s | search s | load <aspell | kjb | lotr | sample> | inorder]");
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

    private static void runTests(Tree<String> tree, int numOfTests) throws IOException {
        String treeType = tree.getClass().getSimpleName();
        String[] fileNames = {"aspell", "kjb", "lotr", "sample"};
        for (String fileName : fileNames) {
            File file = new File("src/tests/" + treeType + "_" + fileName + ".csv");

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(
                    "insert_time;insert_cmp_count;insert_modified_nodes;" +
                    "search_time;search_cmp_count;search_modified_nodes;" +
                    "delete_time;delete_cmp_count;delete_modified_nodes\n"
            );
            long start, end;
            for (int i = 0; i < numOfTests; i++) {
                System.out.println("Testing insert...");
                tree.resetCounters();
                start = System.nanoTime();
                tree.load("src/com/pawelrubin/data/" + fileName + ".txt");
                end = System.nanoTime();
                System.out.println("Testing insert...done");

                fileWriter.append(String.valueOf((end - start) / NANO_TO_SEC)).append(";");
                fileWriter.append(String.valueOf(tree.getCmp_count())).append(";");
                fileWriter.append(String.valueOf(tree.getModifications())).append(";");

                System.out.println("Testing search...");
                tree.resetCounters();
                start = System.nanoTime();
                tree.searchLoaded("src/com/pawelrubin/data/" + fileName + ".txt");
                end = System.nanoTime();
                System.out.println("Testing search...done");

                fileWriter.append(String.valueOf((end - start) / NANO_TO_SEC)).append(";");
                fileWriter.append(String.valueOf(tree.getCmp_count())).append(";");
                fileWriter.append(String.valueOf(tree.getModifications())).append(";");

                System.out.println("Testing delete...");
                tree.resetCounters();
                start = System.nanoTime();
                tree.unload("src/com/pawelrubin/data/" + fileName + ".txt");
                end = System.nanoTime();
                System.out.println("Testing delete...done");

                fileWriter.append(String.valueOf((end - start) / NANO_TO_SEC)).append(";");
                fileWriter.append(String.valueOf(tree.getCmp_count())).append(";");
                fileWriter.append(String.valueOf(tree.getModifications())).append(";");

                fileWriter.append("\n");
            }
            fileWriter.flush();
            fileWriter.close();
        }
        System.out.println("Tests done");
    }

    private static Tree<String> clearTree(String treeType) {
        switch (treeType) {
            case "rbt": return new RBTree<>();
            case "bst": return new BST<>();
            case "splay": return new SplayTree<>();
            default: return null;
        }
    }
}
