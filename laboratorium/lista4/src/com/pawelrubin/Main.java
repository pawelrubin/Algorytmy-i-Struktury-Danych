package com.pawelrubin;

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
                            bst.delete(bst.getRoot());
                            bst.print();
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
            printUsage();
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("usage: ./main [-t <rbt|bst|splay>]");
    }
}
