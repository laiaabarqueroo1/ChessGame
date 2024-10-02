public class Player {
    private String name;
    private List<Piece> alivePieces;
    private List<Piece> deadPieces;

    // Constructor to initialize the player with a set of alive pieces
    public Player(String name, List<Piece> initialPieces) {
        this.name = name;
        this.alivePieces = new ArrayList<>(initialPieces);
        this.deadPieces = new ArrayList<>();
    }

    public List<Piece> getAlivePieces() {
        return alivePieces;
    }

    public List<Piece> getDeadPieces() {
        return deadPieces;
    }

    public String getName() {
        return name;
    }
}
