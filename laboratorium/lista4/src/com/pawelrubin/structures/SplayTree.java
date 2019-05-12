package com.pawelrubin.structures;

public class SplayTree<KeyType extends Comparable<KeyType>> extends BST<KeyType> implements Tree<KeyType> {

    @Override
    protected Node<KeyType> searchNode(Node<KeyType> x, KeyType k) {
        Node<KeyType> node = super.searchNode(x, k);
        if (node != null) {
            while (node.getParent() != null) {
                this.splay(node);
            }
        }
        return node;
    }

    @Override
    protected Node<KeyType> insert(Node<KeyType> z) {
        Node<KeyType> noteToReturn = super.insert(z);
        if (noteToReturn != null) {
            while (noteToReturn.getParent() != null) {
                this.splay(noteToReturn);
            }
        }
        return noteToReturn;
    }

    @Override
    protected Node<KeyType> delete(Node<KeyType> z) {
        Node<KeyType> removed = super.delete(z);
        if (removed != null && removed.getParent() != null) {
            Node<KeyType> parent = removed.getParent();
            while (parent.getParent() != null) {
                this.splay(parent);
            }
        }
        return removed;
    }

    private void splay(Node<KeyType> node) {
        Node<KeyType> parent = node.getParent();
        Node<KeyType> grandParent = (parent != null) ? parent.getParent() : null;
        if (parent != null && parent == root) {
            // Zig step
            root = node;
            node.setParent(null);

            if (node == parent.getLeft()) {
                parent.setLeft(node.getRight());
                if (node.getRight() != null) node.getRight().setParent(parent);
                node.setRight(parent);
                parent.setParent(node);
            } else {
                parent.setRight(node.getLeft());
                if (node.getLeft() != null) node.getLeft().setParent(parent);
                node.setLeft(parent);
                parent.setParent(node);
            }
            return;
        }
        if (parent != null && grandParent != null) {
            Node<KeyType> ggp = grandParent.getParent();
            if (ggp != null && ggp.getLeft() == grandParent) {
                ggp.setLeft(node);
                node.setParent(ggp);
            } else if (ggp != null && ggp.getRight() == grandParent) {
                ggp.setRight(node);
                node.setParent(ggp);
            } else {
                root = node;
                node.setParent(null);
            }

            if ((node == parent.getLeft() && parent == grandParent.getLeft())
                    || (node == parent.getRight() && parent == grandParent.getRight())) {
                // Zig-zig step
                if (node == parent.getLeft()) {
                    Node<KeyType> nr = node.getRight();
                    node.setRight(parent);
                    parent.setParent(node);

                    parent.setLeft(nr);
                    if (nr != null)
                        nr.setParent(parent);

                    Node<KeyType> pr = parent.getRight();
                    parent.setRight(grandParent);
                    grandParent.setParent(parent);

                    grandParent.setLeft(pr);
                    if (pr != null)
                        pr.setParent(grandParent);
                } else {
                    Node<KeyType> nl = node.getLeft();
                    node.setLeft(parent);
                    parent.setParent(node);

                    parent.setRight(nl);
                    if (nl != null)
                        nl.setParent(parent);

                    Node<KeyType> pl = parent.getLeft();
                    parent.setLeft(grandParent);
                    grandParent.setParent(parent);

                    grandParent.setRight(pl);
                    if (pl != null)
                        pl.setParent(grandParent);
                }
                return;
            }

            // Zig-zag step
            if (node == parent.getLeft()) {
                Node<KeyType> nl = node.getRight();
                Node<KeyType> nr = node.getLeft();

                node.setRight(parent);
                parent.setParent(node);

                node.setLeft(grandParent);
                grandParent.setParent(node);

                parent.setLeft(nl);
                if (nl != null)
                    nl.setParent(parent);

                grandParent.setRight(nr);
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
            if (nl != null)
                nl.setParent(parent);

            grandParent.setLeft(nr);
            if (nr != null)
                nr.setParent(grandParent);
        }
    }

}
