package com.pawelrubin.structures;

import java.io.File;

public interface Tree<KeyType extends Comparable<KeyType>> {
    void insert(KeyType value);

    void delete(KeyType value);

    void search(KeyType value);

    void inOrder();

    void load(File f);
}
