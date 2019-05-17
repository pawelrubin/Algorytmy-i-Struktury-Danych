package com.pawelrubin.structures;

public class RBTree<KeyType extends Comparable<KeyType>> extends Tree<KeyType> {
    private ColorNode<KeyType> guard = new ColorNode<>(null);
    private ColorNode<KeyType> root = guard;

    @Override
    public void insert(KeyType value) {
        ColorNode<KeyType> z = new ColorNode<>(value);
        ColorNode<KeyType> y = guard;
        ColorNode<KeyType> x = this.root;

        cmp_count++;
        while (x != guard) {
            y = x;
            cmp_count++;
            if (z.compareTo(x) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        z.setParent(y);
        cmp_count++;
        if (y == guard) {
            this.root = z;
        } else if (z.compareTo(y) < 0) {
            cmp_count++;
            y.setLeft(z);
        } else {
            cmp_count++;
            y.setRight(z);
        }
        z.setLeft(guard);
        z.setRight(guard);
        z.setColor(Color.RED);
        insertFixUp(z);
    }

    @Override
    public void delete(KeyType z) {
        ColorNode<KeyType> toDelete = searchNode(root, z);
        if (toDelete != guard) delete(toDelete);
    }

    @Override
    public void inOrder() {
        System.out.println();
        inorderWalk(this.root);
        System.out.println();
    }

    @Override
    public boolean isEmpty() {
        return root == guard;
    }

    @Override
    public void search(KeyType value) {
        if (searchNode(this.root, value) != guard) {
//            System.out.println("1");
        } else {
//            System.out.println("0");
        }
    }

    private void insertFixUp(ColorNode<KeyType> z) {
        cmp_count++;
        while (z.getParent().getColor() == Color.RED) {
            cmp_count++;
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                ColorNode<KeyType> y = z.getParent().getParent().getRight();
                cmp_count++;
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
                cmp_count++;
                if (y.getColor() == Color.RED) {
                    z.getParent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    z = z.getParent().getParent();
                } else {
                    cmp_count++;
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
        cmp_count++;
        if (x.getRight() != guard) {
            x.getRight().setParent(y);
        }
        x.setParent(y.getParent());
        cmp_count++;
        if (y.getParent() == guard) {
            this.root = x;
        } else if (y == y.getParent().getRight()) {
            cmp_count++;
            y.getParent().setRight(x);
        } else {
            cmp_count++;
            y.getParent().setLeft(x);
        }
        x.setRight(y);
        y.setParent(x);
    }

    private void leftRotate(ColorNode<KeyType> x) {
        ColorNode<KeyType> y = x.getRight();
        x.setRight(y.getLeft());
        cmp_count++;
        if (y.getLeft() != guard) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        cmp_count++;
        if (x.getParent() == guard) {
            this.root = y;
        } else if (x == x.getParent().getLeft()) {
            cmp_count++;
            x.getParent().setLeft(y);
        } else {
            cmp_count++;
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
    }

    private void inorderWalk(ColorNode<KeyType> x) {
        if (x != guard) {
            inorderWalk(x.getLeft());
            System.out.println(x);
            inorderWalk(x.getRight());
        }
    }

    private void delete(ColorNode<KeyType> z) {
        ColorNode<KeyType> y = z;
        Color yOriginalColor = y.getColor();
        ColorNode<KeyType> x;
        cmp_count ++;
        if (z.getLeft() == guard) {
            x = z.getRight();
            transplant(z, z.getRight());
        } else if (z.getRight() == guard) {
            cmp_count ++;
            x = z.getLeft();
            transplant(z, z.getLeft());
        } else {
            cmp_count++;
            y = minimum(z.getRight());
            yOriginalColor = y.getColor();
            x = y.getRight();
            cmp_count ++;
            if (y.getParent() == z) {
                x.setParent(y);
            } else {
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setColor(z.getColor());
        }
        cmp_count ++;
        if (yOriginalColor == Color.BLACK) {
            deleteFixUp(x);
        }
    }

    private void deleteFixUp(ColorNode<KeyType> x) {
        cmp_count += 2;
        while (x != this.root && x.getColor() == Color.BLACK) {
            cmp_count += 3;
            if (x == x.getParent().getLeft()) {
                ColorNode<KeyType> w = x.getParent().getRight();
                cmp_count++;
                if (w.getColor() == Color.RED) {
                    w.setColor(Color.BLACK);
                    x.getParent().setColor(Color.RED);
                    leftRotate(x.getParent());
                    w = x.getParent().getRight();
                }
                cmp_count += 2;
                if (w.getLeft().getColor() == Color.BLACK && w.getRight().getColor() == Color.BLACK) {
                    w.setColor(Color.RED);
                    x = x.getParent();
                } else {
                    cmp_count ++;
                    if (w.getRight().getColor() == Color.BLACK) {
                        w.getLeft().setColor(Color.BLACK);
                        w.setColor(Color.RED);
                        rightRotate(w);
                        w = x.getParent().getRight();
                    }
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(Color.BLACK);
                    w.getRight().setColor(Color.BLACK);
                    leftRotate(x.getParent());
                    x = this.root;
                }
            } else {
                ColorNode<KeyType> w = x.getParent().getLeft();
                cmp_count ++;
                if (w.getColor() == Color.RED) {
                    w.setColor(Color.BLACK);
                    x.getParent().setColor(Color.RED);
                    rightRotate(x.getParent());
                    w = x.getParent().getLeft();
                }
                cmp_count ++;
                if (w.getRight().getColor() == Color.BLACK && w.getLeft().getColor() == Color.BLACK) {
                    w.setColor(Color.RED);
                    x = x.getParent();
                } else {
                    cmp_count ++;
                    if (w.getLeft().getColor() == Color.BLACK) {
                        w.getRight().setColor(Color.BLACK);
                        w.setColor(Color.RED);
                        leftRotate(w);
                        w = x.getParent().getLeft();
                    }
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(Color.BLACK);
                    w.getLeft().setColor(Color.BLACK);
                    rightRotate(x.getParent());
                    x = this.root;
                }
            }
        }
        x.setColor(Color.BLACK);
    }

    private void transplant(ColorNode<KeyType> u, ColorNode<KeyType> v) {
        cmp_count++;
        if (u.getParent() == guard) {
            this.root = v;
        } else if (u == u.getParent().getLeft()) {
            cmp_count++;
            u.getParent().setLeft(v);
        } else {
            cmp_count++;
            u.getParent().setRight(v);
        }
        v.setParent(u.getParent());
    }

    public ColorNode<KeyType> minimum(ColorNode<KeyType> x) {
        cmp_count++;
        while (x.getLeft() != guard) {
            cmp_count++;
            x = x.getLeft();
        }
        return x;
    }

    private ColorNode<KeyType> searchNode(ColorNode<KeyType> node, KeyType key) {
        cmp_count += 2;
        while (node != guard && key.compareTo(node.getKey()) == 0) {
            cmp_count += 3;
            if (key.compareTo(node.getKey()) < 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return node;
    }
}
