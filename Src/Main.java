package Src;

import java.io.IOException;
import java.util.*;

import static Src.PieceInitializer.initializeBlackPieces;

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
        Turns<String> turns = new Turns<>();
        Board.initializeBoard();
        Scanner scanner = new Scanner(System.in);
        boolean gameEnded = false;

        System.out.print("Enter the name of the White player: ");
        String whitePlayerName = scanner.nextLine();
        ArrayList<TypePiece> whitePieces = PieceInitializer.initializeWhitePieces();
        Player<TypePiece> whitePlayer = new Player<>(whitePlayerName, whitePieces);

        System.out.print("Enter the name of the Black player: ");
        String blackPlayerName = scanner.nextLine();
        ArrayList<TypePiece> blackPieces = initializeBlackPieces();
        Player<TypePiece> blackPlayer = new Player<>(blackPlayerName, blackPieces);



        while (!gameEnded) {
            Board.showBoard();
            System.out.print("Enter your move (EXAMPLE: E2 E4....): ");
            String turn = scanner.nextLine();


            // Convert the turn to coordinates and move the pieces
            if (turnToPosition(turn)) {
                turns.addTurn(turn);
                Board.showBoard(); // Show the board after the move


                whitePlayer.printAlivePiecesCount();
                blackPlayer.printAlivePiecesCount();
                whitePlayer.printDeadPiecesCount();
                blackPlayer.printDeadPiecesCount();

                try {
                    checkKingsAlive(); // Se lanza la excepción si se captura el rey
                } catch (FinishGameExcepcion e) {
                    System.out.println(e.getMessage()); // Mostramos el mensaje de finalización
                    gameEnded = true; // Terminamos el bucle del juego
                    break; // Salimos del bucle para finalizar el juego
                }

            } else {
                System.out.println("Invalid move. Please try again.");
            }
        }

        // Save the game to a file at the end
        System.out.print("Enter the filename to save the game: ");
        String filename = scanner.nextLine();
        try {
            turns.saveToFile(filename);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }

    // Replays a game from a file
    public static void replayGame() {
        try {
            Turns<String> turns = readTurns();
            Board.initializeBoard();
            while (turns.getNumberOfTurns() > 0) {
                Board.showBoard();
                String turn = turns.takeFirstTurn();
                System.out.println("Playing move: " + turn);
                turnToPosition(turn);
                Board.showBoard();
            }
        } catch (IOException e) {
            System.out.println("Error loading the game: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Reads the file of turns and returns the list of turns
    public static Turns<String> readTurns() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename to load the game: ");
        String filename = scanner.nextLine();
        try {
            System.out.println(" ");
            return new Turns<>(filename);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return readTurns(); // Recursive call in case of an error
        }
    }


    // Converts a turn (for example, "E2 E4") into coordinates and moves the pieces
    public static boolean turnToPosition(String turn) {
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

    // Converts a position like "E2" into board coordinates
    public static int[] convertPosition(String position) {
        if (position.length() != 2) return null;

        char column = position.charAt(0);
        char row = position.charAt(1);

        int x = 8 - Character.getNumericValue(row); // The row is inverted
        int y = column - 'A';

        if (x < 0 || x >= 8 || y < 0 || y >= 8) return null;

        return new int[]{x, y};
    }

    /*
    // Displays the current chess board
    public static void showBoard() {
        System.out.println("  A B C D E F G H");
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(8 - i);
        }
        System.out.println("  A B C D E F G H");
    }
*/
    /*
    // Initializes the chess board with white and black pieces
    public static void initializeBoard() {
        board = new char[8][8];
        initializeWhitePieces();
        initializeBlackPieces();
        for (int i = 2; i < 6; i++) {
            Arrays.fill(board[i], ' '); // Empty squares
        }
    }

    private static void initializeWhitePieces() {
    }
*/

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

    /*
    // Initializes the white pieces on the board
    public static void initializeWhitePieces() {
        board[6] = new char[]{'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'}; // Pawns
        board[7] = new char[]{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}; // Major pieces
    }

    // Initializes the black pieces on the board
    public static void initializeBlackPieces() {
        board[1] = new char[]{'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'}; // Pawns
        board[0] = new char[]{'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'}; // Major pieces

    }

     */
}
