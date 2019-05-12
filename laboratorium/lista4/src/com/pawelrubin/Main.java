package com.pawelrubin;

import com.pawelrubin.structures.BST;
import com.pawelrubin.structures.RBTree;
import com.pawelrubin.structures.SplayTree;
import com.pawelrubin.structures.Tree;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;

public class Main {

    public static void main(String[] args) {
        Tree<Integer> tree = null;
        try {
            GetOpt getOpt = new GetOpt(args, "t:");
            int c;
            while ((c = getOpt.getNextOption()) != -1) {
                if (c == 't') {
                    String arg = getOpt.getOptionArg();
                    switch (arg) {
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
                tree.insert(21);
                tree.insert(37);
                tree.insert(2137);
                tree.insert(69);
                tree.insert(420);
                tree.inOrder();
                tree.delete(2137);
                tree.inOrder();
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
}
