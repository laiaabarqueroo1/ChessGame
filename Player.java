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

    }

    // Search for a piece at a given position
    public E searchAtPosition(int row, int column) {

    }

    // Remove a piece from a specific position
    public boolean removePieceAtPosition(int column, int row) {

    }
}
