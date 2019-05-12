package com.pawelrubin;

public class Node<KeyType extends Comparable<KeyType>> implements Comparable<Node<KeyType>> {
    private KeyType key;
    private Node<KeyType> left;
    private Node<KeyType> right;
    private Node<KeyType> parent;

    public Node(KeyType key) {
        this.key = key;
    }

    public KeyType getKey() {
        return key;
    }

    public void setKey(KeyType key) {
        this.key = key;
    }

    public Node<KeyType> getLeft() {
        return left;
    }

    public void setLeft(Node<KeyType> left) {
        this.left = left;
    }

    public Node<KeyType> getRight() {
        return right;
    }

    public void setRight(Node<KeyType> right) {
        this.right = right;
    }

    public Node<KeyType> getParent() {
        return parent;
    }

    public void setParent(Node<KeyType> parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(Node<KeyType> node) {
        return this.key.compareTo(node.getKey());
    }
}
