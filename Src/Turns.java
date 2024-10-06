package Src;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Turns<E> {
    // A list that stores the turns of the game
    private ArrayList<E> turnList;

    // Default constructor that initializes an empty turn list
    public Turns() {
        this.turnList = new ArrayList<>();
    }

    // Adds a turn to the turn list
    public void addTurn(E turn) {
        turnList.add(turn); // Add the turn to the list
    }

    // Saves the current turns to a specified file
    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write each turn to the file, followed by a new line
            for (E turn : turnList) {
                writer.write(turn.toString()); // Convert turn to string and write it
                writer.newLine(); // Add a new line after each turn
            }
        }
    }

    // Loads turns from a specified file into the turn list
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read lines from the file until the end
            while ((line = reader.readLine()) != null) {
                turnList.add((E) line); // Cast the line to type E and add it to the list
            }
            System.out.println("Turns loaded successfully."); // Confirm successful loading
        } catch (IOException e) {
            // Print an error message if reading the file fails
            System.out.println("Error reading the file: " + e.getMessage());
            // No recursive call here to avoid stack overflow
        }
    }

    // Takes the first turn from the list and removes it
    public E takeFirstTurn() {
        if (turnList.isEmpty()) {
            System.out.println("There are no extra moves."); // Notify if no turns are left
            return null; // Return null if there are no turns
        }
        return turnList.remove(0); // Remove and return the first turn
    }

    // Gets the number of turns in the turn list
    public int getNumberOfTurns() {
        return turnList.size(); // Return the size of the turn list
    }
}
