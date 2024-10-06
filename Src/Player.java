package Src;

import java.util.ArrayList;
import java.util.List;



public class Player <E extends TypePiece> {
    private String name;
    private ArrayList<E> alivePieces;
    private List<E> deadPieces;

    // Constructor to initialize the player with a set of alive pieces
    public Player(String name, List<E> initialPieces) {
        this.name = name;
        this.alivePieces = new ArrayList<E>(initialPieces);
        this.deadPieces = new ArrayList<>();
    }

    public ArrayList<E> getAlivePieces() {
        return alivePieces;
    }

    public List<E> getDeadPieces() {
        return deadPieces;
    }

    public String getName() {
        return name;
    }

    // Move a piece from one position to another
    public void movePiece(int previousColumn, int previousRow, int newColumn, int newRow) {
        E piece = searchAtPosition(previousRow, previousColumn); // Buscar la pieza en la posición anterior

        if (piece != null) {
            MovablePiece movablePiece = (MovablePiece) piece;  // Cast a pieza movible (si se permite)

            // Verificar si el movimiento es válido para la pieza
            if (movablePiece.isMoveValid(newRow, (char) newColumn)) {
                try {
                    // Actualizar la posición de la pieza internamente
                    piece.setPosicion(newRow, newColumn);

                    // Obtener el tablero actual
                    char[][] board = Board.getBoard();

                    // Mover la pieza en el tablero
                    board[newRow][newColumn] = board[previousRow][previousColumn];  // Poner la pieza en la nueva posición
                    board[previousRow][previousColumn] = ' ';  // Limpiar la posición anterior

                    // Imprimir el movimiento
                    System.out.println("Moved piece to (" + (char)(newColumn + 'A') + ", " + (8 - newRow) + ")");

                } catch (RuntimeException e) {
                    System.out.println("Invalid position: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid move for piece: " + piece.getTypes());  // Mostrar tipo de la pieza
            }
        } else {
            System.out.println("No piece found at (" + (char)(previousColumn + 'A') + ", " + (8 - previousRow) + ")");
        }
    }


    // Search for a piece at a given position
    public E searchAtPosition(int row, int column) {
        for (E piece : alivePieces) {
            if (piece.getRow() == row && piece.getColumn() == column) {
                return piece;
            }
        }
        return null;
    }

    // Remove a piece from a specific position
    public boolean removePieceAtPosition(int column, int row) throws FinishGameExcepcion {
        E piece = searchAtPosition(row, column);
        if (piece != null) {

            // Comprovar si la peça és del mateix jugador
            if (!alivePieces.contains(piece)) {
                System.out.println("You cannot remove your own piece at (" + column + ", " + row + ").");
                return false; // No es pot eliminar una peça del mateix jugador
            }

            // Check if the piece is the king and call finishGame
            if (piece.finishGame()) { // Using the finishGame method to check
                throw new FinishGameExcepcion(); // Throw the exception if the king is captured
            }

            alivePieces.remove(piece);
            deadPieces.add(piece);
            printAlivePiecesCount();
            printDeadPiecesCount();
            System.out.println("Removed piece from (" + column + ", " + row + ")");
            return true;
        } else {
            System.out.println("No piece found at (" + column + ", " + row + ")");
            return false;
        }
    }

    public void printAlivePiecesCount() {
        System.out.println(name + " alive pieces: " + alivePieces.size());
        for (E piece : alivePieces) {
            System.out.print(piece.getTypes() + " ");
        }
        System.out.println(); // Nueva línea al final
    }

    public void printDeadPiecesCount() {
        System.out.println(name + " dead pieces: " + deadPieces.size());
        for (E piece : deadPieces) {
            System.out.print(piece.getTypes() + " ");
        }
        System.out.println(); // Nueva línea al final
    }


}
