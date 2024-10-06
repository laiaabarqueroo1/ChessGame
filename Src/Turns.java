package Src;

import java.io.*;
import java.util.ArrayList;

public class Turns<E> {
    private ArrayList<E> turnList;

    public Turns() {
        this.turnList = new ArrayList<>();
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

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                turnList.add((E) line);
            }
            System.out.println("Turns loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public E takeFirstTurn() {
        if (turnList.isEmpty()) {
            System.out.println("There are no extra moves.");
            return null;
        }
        return turnList.remove(0);
    }

    public int getNumberOfTurns() {
        return turnList.size();
    }
}
