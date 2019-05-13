package com.pawelrubin.structures;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public interface Tree<KeyType extends Comparable<KeyType>> {
    void insert(KeyType value);

    void delete(KeyType value);

    void search(KeyType value);

    void inOrder();

    boolean isEmpty();

    default void load(KeyType fileName) {
        try {
            FileReader fileReader = new FileReader((String) fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Validator<KeyType> validator = new Validator<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                for (String s : split) {
                    KeyType toInsert = validator.fix((KeyType) s);
                    if (toInsert != null) insert(toInsert);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
