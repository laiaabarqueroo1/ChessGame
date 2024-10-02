package Src;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Turns<E> {
    private ArrayList<E> turnList;

    public Turns() {
        this.turnList = new ArrayList<>();
    }

    public Turns(String filename) throws IOException {
        this.turnList = new ArrayList<>();
        loadFromFile(filename);
        if (turnList.isEmpty()) {
            System.out.println("The file is empty or does not contain valid turns.");
        }
    }

    public void addTurn(E turn) {
        turnList.add(turn);
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (E turn : turnList) {
                writer.write(turn.toString());
                writer.newLine();
            }
        }
    }

    private void loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                turnList.add((E) line);
            }
        }
    }

    public E takeFirstTurn() {
        if (turnList.isEmpty()) {
            System.out.println("There are no extra moves.");
            return null; // Return null or throw an exception
        }
        return turnList.remove(0);
    }

    public int getNumberOfTurns() {
        return turnList.size();
    }

    public void printTurns() {
        for (E turn : turnList) {
            System.out.println(turn);
        }
    }
}
