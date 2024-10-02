public class Piece {
    private char Pawn; // peon
    private char Bishop; //alfil
    private char Queen;
    private char King;
    private char Rook; //torre
    private char Knight; //caballos

    private char Colum;
    private int Line;
    private char Type;

    private int Position;

    public Piece(char Type, int Line, char Colum){
        this.Type = Type;
        this.Line = Line;
        this.Colum = Colum;
    }

    public char getColum() {
        return Colum;
    }

    public int getLine() {
        return Line;
    }

    public char getType() {
        return Type;
    }

    public void setPosition(int Line, char Colum){
        this.Line = Line;
        this.Colum = Colum;
    }

    public boolean EndGame(){

        return false;
    }
    public void checkType(char Type){

    }
    public String


}
