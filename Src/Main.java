package Src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        showMenu();
    }

    // Displays the main menu
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 3) {
            System.out.println("Main menu:");
            System.out.println("1. Start new game");
            System.out.println("2. Replay game from a file");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            option = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (option) {
                case 1:
                    playNewGame();
                    break;
                case 2:
                    replayGame();
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Starts a new chess game
    public static void playNewGame() {
        Turns<String> turns = new Turns<>(); // Use the default constructor for turns
        Board.initializeBoard(); // Initialize the chess board

        // Initialize players
        Scanner scanner = new Scanner(System.in);

        // Input for the white player
        System.out.print("Enter the name of the White player: ");
        String whitePlayerName = scanner.nextLine();

        // Fetching initialized white pieces
        ArrayList<Piece> whitePieces = Board.getWhitePieces(); // Get white pieces from the board
        // Ensure Player is compatible with the type of pieces you're using
        Player<Piece> whitePlayer = new Player<>(whitePlayerName, whitePieces); // Correcting Type

        // Input for the black player
        System.out.print("Enter the name of the Black player: ");
        String blackPlayerName = scanner.nextLine();

        // Fetching initialized black pieces
        ArrayList<Piece> blackPieces = Board.getBlackPieces(); // Get black pieces from the board
        // Ensure Player is compatible with the type of pieces you're using
        Player<Piece> blackPlayer = new Player<>(blackPlayerName, blackPieces);


        boolean isWhiteTurn = true;
        boolean gameEnded = false;

        while (!gameEnded) {
            Board.showBoard(); // Display the board
            System.out.print("Enter your move (EXAMPLE: E2 E4....): ");
            String turn = scanner.nextLine();


            String[] positions = turn.split(" ");
            if (positions.length == 2) {
                int[] from = convertPosition(positions[0]);
                int[] to = convertPosition(positions[1]);

                if (from == null || to == null) {
                    System.out.println("Invalid move format. Please use the format (e.g., E2 E4).");
                    continue;
                }

                int previousRow = from[0];
                int previousColumn = from[1];
                int newRow = to[0];
                int newColumn = to[1];

                // Move the piece for the current player
                if (isWhiteTurn) {
                    whitePlayer.movePiece(previousColumn, previousRow, newColumn, newRow);
                    Board.showBoard();
                } else {
                    blackPlayer.movePiece(previousColumn, previousRow, newColumn, newRow);
                    Board.showBoard();
                }

                // Add the turn to the list of turns
                turns.addTurn(turn);
                Board.showBoard(); // Show the board after the move
                whitePlayer.printAlivePiecesCount(); // Show alive pieces count for white
                blackPlayer.printAlivePiecesCount(); // Show alive pieces count for black

                // Check for the status of the kings
                try {
                    checkKingsAlive(); // Check if both kings are alive
                } catch (FinishGameExcepcion e) {
                    System.out.println(e.getMessage()); // Display finish message
                    gameEnded = true; // End the game
                }

                // Alternate turns
                isWhiteTurn = !isWhiteTurn;
            } else {
                System.out.println("Invalid input. Please enter a valid move.");
            }
        }

        // Save the game to a file at the end
        System.out.print("Enter the filename to save the game: ");
        String filename = scanner.nextLine();
        try {
            turns.saveToFile(filename); // Save the turns to a file
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }


    // Replays a game from a file
    public static void replayGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename to load the game: ");
        String filename = scanner.nextLine();

        Turns<String> turns = new Turns<>();
        turns.loadFromFile(filename); // Load turns from a file
        Board.initializeBoard();

        // Simulate the game turn by turn
        while (turns.getNumberOfTurns() > 0) {
            Board.showBoard();
            String turn = turns.takeFirstTurn();
            System.out.println("Playing move: " + turn);

            // Reuse the logic for converting positions
            String[] positions = turn.split(" ");
            int[] from = convertPosition(positions[0]);
            int[] to = convertPosition(positions[1]);

            if (from != null && to != null) {
                // Move the piece on the board during replay
                int previousRow = from[0];
                int previousColumn = from[1];
                int newRow = to[0];
                int newColumn = to[1];

                // Simulate move (assuming the replay doesn't alternate turns)
                Board.getBoard()[newRow][newColumn] = Board.getBoard()[previousRow][previousColumn];
                Board.getBoard()[previousRow][previousColumn] = ' ';
            } else {
                System.out.println("Invalid move in file: " + turn);
            }
        }
    }



        /*
    // Converts a turn (for example, "E2 E4") into coordinates and moves the pieces
    public static boolean turnToPosition(String turn, Player<Piece> currentPlayer, Player<Piece> opponentPlayer) {
        // Basic example of converting a turn into coordinates
        String[] parts = turn.split(" ");
        if (parts.length != 2) return false;

        int[] source = convertPosition(parts[0]);
        int[] destination = convertPosition(parts[1]);

        if (source == null || destination == null) return false;

        // Move the piece from source to destination if it's a valid move
        Board.getBoard()[destination[0]][destination[1]] = Board.getBoard()[source[0]][source[1]];
        Board.getBoard()[source[0]][source[1]] = ' ';
        return true;
    }
*/
    // Converts a position like "E2" into board coordinates
    public static int[] convertPosition (String position) {
        if (position.length() != 2) return null;

        char column = position.charAt(0);
        char row = position.charAt(1);

        int x = 8 - Character.getNumericValue(row); // The row is inverted
        int y = column - 'A';

        if (x < 0 || x >= 8 || y < 0 || y >= 8) return null;

        return new int[]{x, y};
    }

    public static void checkKingsAlive() throws FinishGameExcepcion {
        boolean whiteKingAlive = false;
        boolean blackKingAlive = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Board.getBoard()[i][j] == 'K') {
                    whiteKingAlive = true;
                }
                if (Board.getBoard()[i][j] == 'k') {
                    blackKingAlive = true;
                }
            }
        }

        if (!whiteKingAlive || !blackKingAlive) {
            throw new FinishGameExcepcion();
        }
    }

}
