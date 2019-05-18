package com.pawelrubin.structures;

public class SplayTree<KeyType extends Comparable<KeyType>> extends BST<KeyType> {

    @Override
    protected Node<KeyType> searchNode(Node<KeyType> x, KeyType k) {
        Node<KeyType> node = super.searchNode(x, k);
        cmp_count++;
        if (node != null) {
            cmp_count++;
            while (node.getParent() != null) {
                cmp_count++;
                this.splay(node);
            }
        }
        return node;
    }

    @Override
    protected Node<KeyType> insert(Node<KeyType> z) {
        Node<KeyType> noteToReturn = super.insert(z);
        cmp_count++;
        if (noteToReturn != null) {
            cmp_count++;
            while (noteToReturn.getParent() != null) {
                cmp_count++;
                this.splay(noteToReturn);
            }
        }
        return noteToReturn;
    }

    @Override
    protected Node<KeyType> deleteNode(Node<KeyType> z) {
        Node<KeyType> removed = super.deleteNode(z);
        cmp_count++;
        if (removed != null) {
//            System.out.println("removed: " + removed);
            cmp_count++;
            if (removed.getParent() != null) {
                Node<KeyType> parent = removed.getParent();
                cmp_count++;
                while (parent.getParent() != null) {
                    System.out.println("infinity");
                    cmp_count++;
                    this.splay(parent);
                }
            }
        }
        return removed;
    }

    private void splay(Node<KeyType> node) {
        Node<KeyType> parent = node.getParent();
        cmp_count++;
        Node<KeyType> grandParent = (parent != null) ? parent.getParent() : null;
        cmp_count++;
        if (parent != null) {
            cmp_count++;
            if (parent == root) {
                // Zig step
                root = node;
                modifications++;
                node.setParent(null);

                cmp_count++;
                if (node == parent.getLeft()) {
                    modifications++;
                    parent.setLeft(node.getRight());
                    cmp_count++;
                    if (node.getRight() != null) {
                        modifications++;
                        node.getRight().setParent(parent);
                    }
                    modifications += 2;
                    node.setRight(parent);
                    parent.setParent(node);
                } else {
                    modifications++;
                    parent.setRight(node.getLeft());
                    cmp_count++;
                    if (node.getLeft() != null) {
                        modifications++;
                        node.getLeft().setParent(parent);
                    }
                    node.setLeft(parent);
                    parent.setParent(node);
                }
                return;
            }
        }

        cmp_count++;
        if (parent != null) {
            cmp_count++;
            if (grandParent != null) {
                Node<KeyType> ggp = grandParent.getParent();
                cmp_count += 2;
                if (ggp != null && ggp.getLeft() == grandParent) {
                    ggp.setLeft(node);
                    node.setParent(ggp);
                } else if (ggp != null && ggp.getRight() == grandParent) {
                    cmp_count += 2;
                    ggp.setRight(node);
                    node.setParent(ggp);
                } else {
                    cmp_count += 2;
                    root = node;
                    node.setParent(null);
                }

                if ((node == parent.getLeft() && parent == grandParent.getLeft())
                        || (node == parent.getRight() && parent == grandParent.getRight())) {
                    cmp_count += 4; //big brain
                    // Zig-zig step
                    cmp_count++;
                    if (node == parent.getLeft()) {
                        Node<KeyType> nr = node.getRight();
                        node.setRight(parent);
                        parent.setParent(node);

                        parent.setLeft(nr);
                        cmp_count++;
                        if (nr != null)
                            nr.setParent(parent);

                        Node<KeyType> pr = parent.getRight();
                        parent.setRight(grandParent);
                        grandParent.setParent(parent);

                        grandParent.setLeft(pr);
                        cmp_count++;
                        if (pr != null)
                            pr.setParent(grandParent);
                    } else {
                        Node<KeyType> nl = node.getLeft();
                        node.setLeft(parent);
                        parent.setParent(node);

                        parent.setRight(nl);
                        cmp_count++;
                        if (nl != null)
                            nl.setParent(parent);

                        Node<KeyType> pl = parent.getLeft();
                        parent.setLeft(grandParent);
                        grandParent.setParent(parent);

                        grandParent.setRight(pl);
                        cmp_count++;
                        if (pl != null)
                            pl.setParent(grandParent);
                    }
                    return;
                }

                // Zig-zag step
                cmp_count++;
                if (node == parent.getLeft()) {
                    Node<KeyType> nl = node.getRight();
                    Node<KeyType> nr = node.getLeft();

                    node.setRight(parent);
                    parent.setParent(node);

                    node.setLeft(grandParent);
                    grandParent.setParent(node);

                    parent.setLeft(nl);
                    cmp_count++;
                    if (nl != null)
                        nl.setParent(parent);

                    grandParent.setRight(nr);
                    cmp_count++;
                    if (nr != null)
                        nr.setParent(grandParent);
                    return;
                }

                Node<KeyType> nl = node.getLeft();
                Node<KeyType> nr = node.getRight();

                node.setLeft(parent);
                parent.setParent(node);

                node.setRight(grandParent);
                grandParent.setParent(node);

                parent.setRight(nl);
                cmp_count++;
                if (nl != null)
                    nl.setParent(parent);

                grandParent.setLeft(nr);
                cmp_count++;
                if (nr != null)
                    nr.setParent(grandParent);
            }
        }
    }
}
