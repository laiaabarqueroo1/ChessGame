package Src;

import java.util.ArrayList;
import java.util.List;

public class Player<E extends TypePiece> {
    private String name;
    private ArrayList<E> alivePieces;
    private List<E> deadPieces;

    // Constructor para inicializar el jugador con un conjunto de piezas vivas
    public Player(String name, List<E> initialPieces) {
        this.name = name;
        this.alivePieces = new ArrayList<>(initialPieces);
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

    public boolean movePiece(String from, String to, boolean isWhiteTurn) {
        // Convertir las posiciones de cadena a coordenadas
        int[] source = convertPosition(from);
        int[] destination = convertPosition(to);

        // Verificar si la conversión fue exitosa
        if (source == null || destination == null) {
            System.out.println("Invalid position format.");
            return false;
        }

        int previousRow = source[0];
        int previousColumn = source[1];
        int newRow = destination[0];
        int newColumn = destination[1];

        // Buscar la pieza en la posición anterior
        E piece = searchAtPosition(previousRow, previousColumn);

        // Verificar si se encontró una pieza
        if (piece != null) {
            System.out.println("Piece found: " + piece.getTypes() + " of class: " + piece.getClass().getName());

            // Verificar si el movimiento es válido utilizando el método de la clase Piece
            if (piece instanceof Piece) { // Verificamos si es instancia de Piece
                Piece actualPiece = (Piece) piece; // Hacemos cast a Piece
                if (actualPiece.isMoveValid(newRow, (char) (newColumn + 'A'), isWhiteTurn)) { // Usar el nuevo parámetro
                    char[][] board = Board.getBoard();

                    // Verificar si la posición de destino está ocupada por una pieza del oponente
                    if (board[newRow][newColumn] != ' ' &&
                            Character.isLowerCase(board[newRow][newColumn]) != Character.isLowerCase(actualPiece.getTypes())) {
                        // Llamar a removePieceAtPosition para "matar" la pieza contraria
                        try {
                            removePieceAtPosition(newColumn, newRow); // Matar la pieza
                        } catch (FinishGameExcepcion e) {
                            System.out.println(e.getMessage());
                            return false; // Si el juego termina por la captura del rey, no se puede mover
                        }
                    }

                    // Mover la pieza
                    board[newRow][newColumn] = actualPiece.getTypes();
                    board[previousRow][previousColumn] = ' '; // Limpiar la posición anterior
                    actualPiece.setPosicion(newRow, newColumn); // Actualizar la posición de la pieza

                    System.out.println("Moved piece to (" + (char) (newColumn + 'A') + ", " + (8 - newRow) + ")");
                    return true; // Retorna true si el movimiento fue exitoso
                } else {
                    System.out.println("Invalid move for piece: " + actualPiece.getTypes());
                }
            } else {
                System.out.println("The piece is not an instance of Piece class.");
            }
        } else {
            System.out.println("No piece found at specified position.");
        }

        return false; // Retorna false si no se pudo mover la pieza
    }


    // Remove a piece from a specific position
    public boolean removePieceAtPosition(int column, int row) throws FinishGameExcepcion {
        E piece = searchAtPosition(row, column);
        if (piece != null) {
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




    public E searchAtPosition(int row, int column) {
        System.out.println("Searching for piece at row: " + row + ", column: " + column);

        for (E piece : alivePieces) {
            int pieceColumnIndex = piece.getColumn() - 'A'; // Suponiendo que piece.getColumn() es un char
            System.out.println("Checking piece: " + piece.getTypes() + " at (" + piece.getRow() + ", " + pieceColumnIndex + ")");

            // Comparamos la fila y la columna convertida
            if (piece.getRow() == row && pieceColumnIndex == column) {
                System.out.println("Piece found at position: (" + row + ", " + column + ")");
                return piece; // Si se encuentra la pieza, se retorna
            }
        }

        System.out.println("No piece found at specified position.");
        return null; // Si no se encuentra la pieza en la posición dada, retorna null
    }


    public int[] convertPosition(String position) {
        if (position.length() != 2) {
            System.out.println("Invalid position format: " + position);
            return null;
        }

        char column = position.charAt(0); // 'A' a 'H'
        char row = position.charAt(1); // '1' a '8'

        // Validar que la columna esté entre 'A' y 'H'
        if (column < 'A' || column > 'H') {
            System.out.println("Invalid column: " + column);
            return null;
        }

        // Validar que la fila esté entre '1' y '8'
        if (row < '1' || row > '8') {
            System.out.println("Invalid row: " + row);
            return null;
        }

        // Ajustamos la conversión:
        int x = 8 - Character.getNumericValue(row); // La fila debe ser 0 a 7
        int y = column - 'A'; // Convertir columna 'A'-'H' a 0-7

        // Mostrar el valor de y
        System.out.println("Converted position " + position + " to coordinates: (" + x + ", " + y + ")");

        // Retornamos la posición como {fila, columna}
        return new int[]{x, y};
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
