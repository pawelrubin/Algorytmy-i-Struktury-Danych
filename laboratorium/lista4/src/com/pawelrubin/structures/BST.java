package com.pawelrubin.structures;

import java.util.Stack;

public class BST<KeyType extends Comparable<KeyType>> extends Tree<KeyType> {
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
        Node<KeyType> toDelete = searchNode(root, z);
        if (toDelete != null) deleteNode(toDelete);
    }

    @Override
    public void search(KeyType value) {
        if (searchNode(this.root, value) != null) {
//            System.out.println("1");
        } else {
//            System.out.println("0");
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

        cmp_count++;
        while (x != null) {
            y = x;
            cmp_count += 2;
            if (z.compareTo(x) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        modifications++;
        z.setParent(y);

        cmp_count++;
        if (y == null) {
            modifications++;
            this.root = z;
        } else if (z.compareTo(y) < 0) {
            cmp_count++;
            modifications++;
            y.setLeft(z);
        } else {
            cmp_count++;
            modifications++;
            y.setRight(z);
        }

        return z;
    }

    protected Node<KeyType> transplant(Node<KeyType> u, Node<KeyType> v) {
        cmp_count++;
        if (u.getParent() == null) {
            modifications++;
            this.root = v;
        } else if (u == u.getParent().getLeft()) {
            cmp_count++;
            modifications++;
            u.getParent().setLeft(v);
        } else {
            cmp_count++;
            modifications++;
            u.getParent().setRight(v);
        }
        cmp_count++;
        if (v != null) {
            modifications++;
            v.setParent(u.getParent());
        }
        return v;
    }

    protected Node<KeyType> deleteNode(Node<KeyType> z) {
        cmp_count++;
        if (z.getLeft() == null) {
            return transplant(z, z.getRight());
        } else if (z.getRight() == null) {
            cmp_count++;
            return transplant(z, z.getLeft());
        } else {
            cmp_count++;
            Node<KeyType> y = minimum(z.getRight());
            cmp_count++;
            if (y.getParent() != z) {
                transplant(y, y.getRight());
                modifications += 2;
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(z, y);
            modifications += 2;
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            return y;
        }
    }

    protected Node<KeyType> searchNode(Node<KeyType> node, KeyType key) {
        cmp_count += 2;
        while (node != null && key.compareTo(node.getKey()) != 0) {
            cmp_count += 3;
            if (key.compareTo(node.getKey()) < 0) {
                node = node.getLeft();
            } else if (key.compareTo(node.getKey()) > 0) {
                node = node.getRight();
            } else {
                return node;
            }
        }
        return node;
    }

    protected Node<KeyType> minimum(Node<KeyType> x) {
        cmp_count++;
        while (x.getLeft() != null) {
            cmp_count++;
            x = x.getLeft();
        }
        return x;
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
    }

}
