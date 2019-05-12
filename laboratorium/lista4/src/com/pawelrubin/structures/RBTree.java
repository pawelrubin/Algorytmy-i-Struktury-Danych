package com.pawelrubin.structures;

public class RBTree<KeyType extends Comparable<KeyType>> {
    private ColorNode<KeyType> guard = new ColorNode<>(null);
    private ColorNode<KeyType> root = guard;

    public void insert(KeyType value) {
        ColorNode<KeyType> z = new ColorNode<>(value);
        ColorNode<KeyType> y = guard;
        ColorNode<KeyType> x = this.root;

        while (x != guard) {
            y = x;
            if (z.compareTo(x) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        z.setParent(y);
        if (y == guard) {
            this.root = z;
        } else if (z.compareTo(y) < 0) {
            y.setLeft(z);
        } else {
            y.setRight(z);
        }
        z.setLeft(guard);
        z.setRight(guard);
        z.setColor(Color.RED);
        insertFixUp(z);
    }

    private void insertFixUp(ColorNode<KeyType> z) {
        while (z.getParent().getColor() == Color.RED) {
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                ColorNode<KeyType> y = z.getParent().getParent().getRight();
                if (y.getColor() == Color.RED) {
                    z.getParent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getRight()) {
                        z = z.getParent();
                        leftRotate(z);
                    }
                    z.getParent().setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    rightRotate(z.getParent().getParent());
                }
            } else {
                ColorNode<KeyType> y = z.getParent().getParent().getLeft();
                if (y.getColor() == Color.RED) {
                    z.getParent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getLeft()) {
                        z = z.getParent();
                        rightRotate(z);
                    }
                    z.getParent().setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    leftRotate(z.getParent().getParent());
                }
            }
        }
        this.root.setColor(Color.BLACK);

    }

    private void rightRotate(ColorNode<KeyType> y) {
        ColorNode<KeyType> x = y.getLeft();
        y.setLeft(x.getRight());
        if (x.getRight() != guard) {
            x.getRight().setParent(y);
        }
        x.setParent(y.getParent());
        if (y.getParent() == guard) {
            this.root = x;
        } else if (y == y.getParent().getRight()) {
            y.getParent().setRight(x);
        } else {
            y.getParent().setLeft(x);
        }
        x.setRight(y);
        y.setParent(x);
    }

    private void leftRotate(ColorNode<KeyType> x) {
        ColorNode<KeyType> y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != guard) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == guard) {
            this.root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
    }

    public void print() {
        inorderWalk(this.root);
    }

    private void inorderWalk(ColorNode<KeyType> x) {
        if (x != guard) {
            inorderWalk(x.getLeft());
            System.out.println(x);
            inorderWalk(x.getRight());
        }
    }
}
