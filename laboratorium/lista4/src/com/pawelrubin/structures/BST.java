package com.pawelrubin.structures;

public class BST<KeyType extends Comparable<KeyType>> {
    private Node<KeyType> root;

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

    public void inorderWalk(Node<KeyType> x) {
        if (x != null) {
            inorderWalk(x.getLeft());
            System.out.println(x.getKey());
            inorderWalk(x.getRight());
        }
    }

    public void transplant(Node<KeyType> u, Node<KeyType> v) {
        if (u.getParent() == null) {
            this.root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    public void delete(KeyType z) {
        delete(iterativeSearch(root, z));
    }

    private void delete(Node<KeyType> z) {
        if (z.getLeft() == null) {
            transplant(z, z.getRight());
        } else if (z.getRight() == null) {
            transplant(z, z.getLeft());
        } else {
            Node<KeyType> y = minimum(z.getRight());
            if (y.getParent() != z) {
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
        }
    }

    public void print() {
        System.out.println();
        inorderWalk(this.root);
        System.out.println();
    }

    public void search(KeyType value) {
        if (iterativeSearch(this.root, value) != null) {
            System.out.println("1");
        } else {
            System.out.println("0");
        }
    }

    public Node<KeyType> iterativeSearch(Node<KeyType> x, KeyType k) {
        while (x != null && k != x.getKey()) {
            if (k.compareTo(x.getKey()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }
        return x;
    }

    public Node<KeyType> minimum(Node<KeyType> x) {
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x;
    }

    public Node<KeyType> getRoot() {
        return root;
    }
}
