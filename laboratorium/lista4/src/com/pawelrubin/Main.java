package com.pawelrubin;

import com.pawelrubin.structures.BST;
import com.pawelrubin.structures.RBTree;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;

public class Main {

    public static void main(String[] args) {
        try {
            GetOpt getOpt = new GetOpt(args, "t:");
            int c;
            while ((c = getOpt.getNextOption()) != -1) {
                if (c == 't') {
                    String arg = getOpt.getOptionArg();
                    switch (arg) {
                        case "rbt": {
                            System.out.println("Simulating rbt");
                            RBTree<Integer> rbt = new RBTree<>();
                            rbt.insert(2137);
                            rbt.insert(21);
                            rbt.insert(37);
                            rbt.insert(69);
                            rbt.print();
                            rbt.delete(2137);
                            rbt.print();
                            break;
                        }
                        case "bst": {
                            System.out.println("Simulating bst");
                            BST<Integer> bst = new BST<>();
                            bst.insert(6);
                            bst.insert(5);
                            bst.insert(2);
                            bst.insert(5);
                            bst.insert(7);
                            bst.insert(8);
                            bst.print();
                            bst.delete(6);
                            bst.print();
                            bst.search(6);
                            bst.search(2);
                            break;
                        }
                        case "splay": {
                            System.out.println("Simulating splay");
                            break;
                        }
                        default:
                            throw new IllegalArgumentException();
                    }
                } else {
                    printUsage();
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
}
