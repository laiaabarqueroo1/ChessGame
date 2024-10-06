package Src;

import java.util.ArrayList;
import java.util.List;

public class Player<E extends TypePiece> {
    private String name;
    private ArrayList<E> alivePieces;
    private List<E> deadPieces;

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

    public boolean movePiece(String from, String to, boolean isWhiteTurn) throws FinishGameExcepcion {
        int[] source = convertPosition(from);
        int[] destination = convertPosition(to);

        if (source == null || destination == null) {
            printError("Invalid position format.");
            return false;
        }

        int previousRow = source[0];
        int previousColumn = source[1];
        int newRow = destination[0];
        int newColumn = destination[1];

        E piece = searchAtPosition(previousRow, previousColumn);

        if (piece != null && piece instanceof Piece) {
            Piece actualPiece = (Piece) piece;
            if (actualPiece.isMoveValid(newRow, (char) (newColumn + 'A'), isWhiteTurn)) {
                char[][] board = Board.getBoard();
                handleCapture(board, newRow, newColumn, actualPiece);

                // Mover la pieza
                if (!isWhiteTurn) {
                    board[newRow][newColumn] = Character.toLowerCase(actualPiece.getTypes());
                } else {
                    board[newRow][newColumn] = actualPiece.getTypes();
                }
                board[previousRow][previousColumn] = ' ';
                actualPiece.setPosicion(newRow, newColumn);

                printSuccess(String.format("Moved piece to (%s, %d)", (char) (newColumn + 'A'), (8 - newRow)));
                return true;
            } else {
                printError(String.format("Invalid move for piece: %s", actualPiece.getTypes()));
            }
        } else {
            printError("No piece found at specified position.");
        }

        return false;
    }

    private void handleCapture(char[][] board, int newRow, int newColumn, Piece actualPiece) throws FinishGameExcepcion {
        if (board[newRow][newColumn] != ' ' &&
                Character.isLowerCase(board[newRow][newColumn]) != Character.isLowerCase(actualPiece.getTypes())) {
            removePieceAtPosition(newColumn, newRow);
        }
    }

    public boolean removePieceAtPosition(int column, int row) throws FinishGameExcepcion {
        E piece = searchAtPosition(row, column);
        if (piece != null) {
            if (piece.finishGame()) {
                throw new FinishGameExcepcion();
            }

            alivePieces.remove(piece);
            deadPieces.add(piece);

            printSuccess("Piece removed: " + piece.getTypes());
            printAlivePiecesCount();
            printDeadPiecesCount();

            return true;
        } else {
            printError(String.format("No piece found at (%d, %d)", column, row));
            return false;
        }
    }

    public E searchAtPosition(int row, int column) {
        for (E piece : alivePieces) {
            int pieceColumnIndex = piece.getColumn() - 'A';
            if (piece.getRow() == row && pieceColumnIndex == column) {
                return piece;
            }
        }

        return null;
    }

    public int[] convertPosition(String position) {
        if (position.length() != 2) {
            printError("Invalid position format: " + position);
            return null;
        }

        char column = position.charAt(0);
        char row = position.charAt(1);

        if (column < 'A' || column > 'H') {
            printError("Invalid column: " + column);
            return null;
        }

        if (row < '1' || row > '8') {
            printError("Invalid row: " + row);
            return null;
        }

        int x = 8 - Character.getNumericValue(row);
        int y = column - 'A';

        return new int[]{x, y};
    }

    private void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    private void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printAlivePiecesCount() {
        System.out.println("\n" + name + "'s Alive Pieces:");
        System.out.println("=====================");
        System.out.println("Count: " + alivePieces.size());
        alivePieces.forEach(piece -> System.out.print(piece.getTypes() + " "));
        System.out.println("\n");
    }

    public void printDeadPiecesCount() {
        System.out.println("\n" + name + "'s Dead Pieces:");
        System.out.println("=====================");
        System.out.println("Count: " + deadPieces.size());
        deadPieces.forEach(piece -> System.out.print(piece.getTypes() + " "));
        System.out.println("\n");
    }
}
