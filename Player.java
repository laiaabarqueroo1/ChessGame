import java.util.ArrayList;
import java.util.List;

public class Player <E extends TypePiece> {
    private String name;
    private ArrayList<E> alivePieces;
    private List<E> deadPieces;

    // Constructor to initialize the player with a set of alive pieces
    public Player(String name, List<Piece> initialPieces) {
        this.name = name;
        this.alivePieces = new ArrayList<>(initialPieces);
        this.deadPieces = new ArrayList<>();
    }

    public ArrayList<E> getAlivePieces() {
        return alivePieces;
    }

    public List<Piece> getDeadPieces() {
        return deadPieces;
    }

    public String getName() {
        return name;
    }

    / Move a piece from one position to another
    public void movePiece(int previousColumn, int previousRow, int newColumn, int newRow) {
        E piece = searchAtPosition(row, column);
        if (piece != null) {
            alivePieces.remove(piece);
            deadPieces.add(piece);
            System.out.println("Removed piece from (" + column + ", " + row + ")");
            return true;
        } else {
            System.out.println("No piece found at (" + column + ", " + row + ")");
            return false;
        }
    }

    // Search for a piece at a given position
    public E searchAtPosition(int row, int column) {
        for (E piece : alivePieces) {
            if (piece.getFila() == row && piece.getColumna() == column) {
                return piece;
            }
        }
        return null; // No piece found at the given position
    }
    }

    // Remove a piece from a specific position
    public boolean removePieceAtPosition(int column, int row) {
        E piece = searchAtPosition(row, column);
        if (piece != null) {
            alivePieces.remove(piece);
            deadPieces.add(piece);
            System.out.println("Removed piece from (" + column + ", " + row + ")");
            return true;
        } else {
            System.out.println("No piece found at (" + column + ", " + row + ")");
            return false;
        }
    }
}
