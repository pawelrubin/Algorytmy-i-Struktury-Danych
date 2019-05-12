package com.pawelrubin.structures;


public class ColorNode<KeyType extends Comparable<KeyType>> implements Comparable<ColorNode<KeyType>>{
    private Color color;
    private KeyType key;
    private ColorNode<KeyType> left = null;
    private ColorNode<KeyType> right = null ;
    private ColorNode<KeyType> parent = null;

    public ColorNode(KeyType key) {
        this.key = key;
    }

    @Override
    public int compareTo(ColorNode<KeyType> node) {
        return this.key.compareTo(node.getKey());
    }

    @Override
    public String toString() {
        String result = this.key.toString() + " " + this.color.toString();
        if (this.parent != null && this.parent.key != null) {
            result += " " + this.parent.key + " " + this.color;
        } else {
            result += " null";
        }
        return result;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ColorNode<KeyType> getLeft() {
        return left;
    }

    public void setLeft(ColorNode<KeyType> left) {
        this.left = left;
    }

    public ColorNode<KeyType> getRight() {
        return right;
    }

    public void setRight(ColorNode<KeyType> right) {
        this.right = right;
    }

    public ColorNode<KeyType> getParent() {
        return parent;
    }

    public void setParent(ColorNode<KeyType> parent) {
        this.parent = parent;
    }

    public KeyType getKey() {
        return this.key;
    }

    public void setKey(KeyType key) {
        this.key = key;
    }
}

enum Color {
    RED,
    BLACK
}
