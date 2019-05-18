package com.pawelrubin.structures;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Tree<KeyType extends Comparable<KeyType>> {

    protected int cmp_count = 0;
    protected int modifications = 0;

    public abstract void insert(KeyType value);

    public abstract void delete(KeyType value);

    public abstract void search(KeyType value);

    public abstract void inOrder();

    abstract boolean isEmpty();

    public void load(KeyType fileName) {
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
            e.printStackTrace();
            System.out.println("File not found...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unload(KeyType fileName) {
        try {
            FileReader fileReader = new FileReader((String) fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Validator<KeyType> validator = new Validator<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                for (String s : split) {
                    KeyType toDelete = validator.fix((KeyType) s);
                    if (toDelete != null) delete(toDelete);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchLoaded(KeyType fileName) {
        try {
            FileReader fileReader = new FileReader((String) fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Validator<KeyType> validator = new Validator<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                for (String s : split) {
                    KeyType toSearch = validator.fix((KeyType) s);
                    if (toSearch != null) search(toSearch);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetCounters() {
        this.cmp_count = 0;
        this.modifications = 0;
    }

    public int getModifications() {
        return modifications;
    }

    public int getCmp_count() {
        return cmp_count;
    }
}
