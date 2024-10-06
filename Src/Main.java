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
        scanner.close(); // Close scanner to avoid resource leak
    }

    public static void playNewGame() {
        // Inicializar el tablero y los jugadores
        Turns<String> turns = new Turns<>();
        Board.initializeBoard();
        Scanner scanner = new Scanner(System.in);

        // Pedir los nombres de los jugadores
        System.out.print("Enter the name of the White player: ");
        String whitePlayerName = scanner.nextLine();
        ArrayList<Piece> whitePieces = Board.getWhitePieces();
        Player<Piece> whitePlayer = new Player<>(whitePlayerName, whitePieces);

        System.out.print("Enter the name of the Black player: ");
        String blackPlayerName = scanner.nextLine();
        ArrayList<Piece> blackPieces = Board.getBlackPieces();
        Player<Piece> blackPlayer = new Player<>(blackPlayerName, blackPieces);

        boolean isWhiteTurn = true;
        boolean gameEnded = false;

        while (!gameEnded) {
            Board.showBoard(); // Mostrar el tablero
            System.out.print("Enter your move (EXAMPLE: E2 E4): ");
            String turn = scanner.nextLine();

            String[] positions = turn.split(" ");
            if (positions.length == 2) {
                if (isWhiteTurn) {
                    if (!whitePlayer.movePiece(positions[0], positions[1], isWhiteTurn)) {
                        System.out.println("Invalid move for White player.");
                        continue;
                    }
                } else {
                    if (!blackPlayer.movePiece(positions[0], positions[1], isWhiteTurn)) {
                        System.out.println("Invalid move for Black player.");
                        continue;
                    }
                }

                turns.addTurn(turn);
                whitePlayer.printAlivePiecesCount();
                blackPlayer.printAlivePiecesCount();

                try {
                    checkKingsAlive(); // Comprobar si ambos reyes están vivos
                } catch (FinishGameExcepcion e) {
                    System.out.println(e.getMessage());
                    gameEnded = true;
                }

                isWhiteTurn = !isWhiteTurn; // Alternar turnos
            } else {
                System.out.println("Invalid input. Please enter a valid move.");
            }
        }

        // Guardar el juego en un archivo al final
        System.out.print("Enter the filename to save the game: ");
        String filename = scanner.nextLine();
        try {
            turns.saveToFile(filename);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }



    public static void replayGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename to load the game: ");
        String filename = scanner.nextLine();

        Turns<String> turns = new Turns<>();

        // Cargar los turnos desde el archivo
        turns.loadFromFile(filename); // Load turns from a file

        // Mostrar los turnos cargados
        System.out.println("Loaded turns:");
        for (int i = 0; i < turns.getNumberOfTurns(); i++) {
            System.out.println(turns.takeFirstTurn()); // Mostrar cada turno
        }

        // Al final, volver al menú principal
        System.out.println("Replay finished. Returning to main menu.");
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
