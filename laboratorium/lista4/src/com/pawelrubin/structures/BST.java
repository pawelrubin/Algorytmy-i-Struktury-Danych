package com.pawelrubin.structures;

import java.util.Stack;

public class BST<KeyType extends Comparable<KeyType>> implements Tree<KeyType> {
    protected Node<KeyType> root;

    public BST() {
        root = null;
    }

    @Override
    public void insert(KeyType z) {
        insert(new Node<>(z));
    }

    @Override
    public void delete(KeyType z) {
        delete(searchNode(root, z));
    }

    @Override
    public void search(KeyType value) {
        if (searchNode(this.root, value) != null) {
            System.out.println("1");
        } else {
            System.out.println("0");
        }
    }

    @Override
    public void inOrder() {
        System.out.println();
        inorderWalk(this.root);
        System.out.println();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    protected Node<KeyType> insert(Node<KeyType> z) {
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

        return z;
    }

    private void inorderWalk(Node<KeyType> x) {
        if (x == null) return;

        Stack<Node<KeyType>> s = new Stack<>();
        Node<KeyType> curr = x;
        while (curr != null || s.size() > 0) {
            while (curr != null) {
                s.push(curr);
                curr = curr.getLeft();
            }

            curr = s.pop();

            System.out.println(curr);

            curr = curr.getRight();
        }
//        recursive:
//        if (x != null) {
//            inorderWalk(x.getLeft());
//            System.out.println(x);
//            inorderWalk(x.getRight());
//        }
    }

    protected Node<KeyType> transplant(Node<KeyType> u, Node<KeyType> v) {
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
        return v;
    }

    protected Node<KeyType> delete(Node<KeyType> z) {
        if (z.getLeft() == null) {
            return transplant(z, z.getRight());
        } else if (z.getRight() == null) {
            return transplant(z, z.getLeft());
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
            return y;
        }
    }

    protected Node<KeyType> searchNode(Node<KeyType> node, KeyType s) {
        if (node == null || s.compareTo(node.getKey()) == 0) return node;
        if (s.compareTo(node.getKey()) < 0) return searchNode(node.getLeft(), s);
        else return searchNode(node.getRight(), s);
    }

    protected Node<KeyType> minimum(Node<KeyType> x) {
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x;
    }

}
