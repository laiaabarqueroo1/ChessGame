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
            printError("Invalid position format.");
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
            // Verificar si el movimiento es válido utilizando el método de la clase Piece
            if (piece instanceof Piece) {
                Piece actualPiece = (Piece) piece;
                if (actualPiece.isMoveValid(newRow, (char) (newColumn + 'A'), isWhiteTurn)) {
                    char[][] board = Board.getBoard();

                    // Verificar si la posición de destino está ocupada por una pieza del oponente
                    if (board[newRow][newColumn] != ' ' &&
                            Character.isLowerCase(board[newRow][newColumn]) != Character.isLowerCase(actualPiece.getTypes())) {
                        // Llamar a removePieceAtPosition para "matar" la pieza contraria
                        try {
                            removePieceAtPosition(newColumn, newRow);
                        } catch (FinishGameExcepcion e) {
                            printError(e.getMessage());
                            return false;
                        }
                    }

                    // Mover la pieza
                    board[newRow][newColumn] = actualPiece.getTypes();
                    board[previousRow][previousColumn] = ' ';
                    actualPiece.setPosicion(newRow, newColumn);

                    printSuccess(String.format("Moved piece to (%s, %d)", (char) (newColumn + 'A'), (8 - newRow)));
                    return true;
                } else {
                    printError(String.format("Invalid move for piece: %s", actualPiece.getTypes()));
                }
            } else {
                printError("The piece is not an instance of Piece class.");
            }
        } else {
            printError("No piece found at specified position.");
        }

        return false;
    }

    // Remove a piece from a specific position
    public boolean removePieceAtPosition(int column, int row) throws FinishGameExcepcion {
        E piece = searchAtPosition(row, column);
        if (piece != null) {
            // Check if the piece is the king and call finishGame
            if (piece.finishGame()) {
                throw new FinishGameExcepcion();
            }

            alivePieces.remove(piece);
            deadPieces.add(piece);
            printAlivePiecesCount();
            printDeadPiecesCount();
            printSuccess(String.format("Removed piece from (%d, %d)", column, row));
            return true;
        } else {
            printError(String.format("No piece found at (%d, %d)", column, row));
            return false;
        }
    }

    public E searchAtPosition(int row, int column) {
        printInfo(String.format("Searching for piece at row: %d, column: %d", row, column));

        for (E piece : alivePieces) {
            int pieceColumnIndex = piece.getColumn() - 'A'; // Assuming piece.getColumn() is a char

            // Compare row and converted column
            if (piece.getRow() == row && pieceColumnIndex == column) {
                printSuccess(String.format("Piece found at position: (%d, %d)", row, column));
                return piece;
            }
        }

        printError("No piece found at specified position.");
        return null;
    }

    public int[] convertPosition(String position) {
        if (position.length() != 2) {
            printError("Invalid position format: " + position);
            return null;
        }

        char column = position.charAt(0); // 'A' a 'H'
        char row = position.charAt(1); // '1' a '8'

        // Validate that the column is between 'A' and 'H'
        if (column < 'A' || column > 'H') {
            printError("Invalid column: " + column);
            return null;
        }

        // Validate that the row is between '1' and '8'
        if (row < '1' || row > '8') {
            printError("Invalid row: " + row);
            return null;
        }

        // Adjust conversion:
        int x = 8 - Character.getNumericValue(row); // Row should be 0 to 7
        int y = column - 'A'; // Convert column 'A'-'H' to 0-7

        printInfo(String.format("Converted position %s to coordinates: (%d, %d)", position, x, y));

        // Return position as {row, column}
        return new int[]{x, y};
    }

    private void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    private void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public void printAlivePiecesCount() {
        System.out.println("\n" + name + "'s Alive Pieces:");
        System.out.println("=====================");
        System.out.println("Count: " + alivePieces.size());
        for (E piece : alivePieces) {
            System.out.print(piece.getTypes() + " ");
        }
        System.out.println();
    }

    public void printDeadPiecesCount() {
        System.out.println("\n" + name + "'s Dead Pieces:");
        System.out.println("=====================");
        System.out.println("Count: " + deadPieces.size());
        for (E piece : deadPieces) {
            System.out.print(piece.getTypes() + " ");
        }
        System.out.println();
    }
}
