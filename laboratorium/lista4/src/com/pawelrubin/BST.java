package com.pawelrubin;

import java.util.ArrayList;

public class BST<KeyType extends Comparable<KeyType>> {
    private Node<KeyType> root;
    private ArrayList<Node<KeyType>> nodes = new ArrayList<>();

    public BST() {
        root = null;
    }

    public void insert(KeyType value) {
        Node<KeyType> z = new Node<>(value);
        Node<KeyType> y = null;
        Node<KeyType> x = this.root;

        while (x != null) {
            y = x;
            if (z.compareTo(x) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        z.setParent(y);
        if (y == null) {
            this.root = z;
        } else if (z.compareTo(y) < 0) {
            y.setLeft(z);
        } else {
            y.setRight(z);
        }
    }

    public void walk(Node<KeyType> x) {
        if (x != null) {
            walk(x.getLeft());
            System.out.println(x.getKey());
            walk(x.getRight());
        }
    }

    public Node<KeyType> getRoot() {
        return root;
    }
}
