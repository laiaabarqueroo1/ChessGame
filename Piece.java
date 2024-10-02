public class Piece implements TypePiece {
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

    private void checkType(char Type){
        try {

        }catch (Exception e){

        }
    }

    public String toString(){

    }
    public boolean equals(Object o) {

    }

    @Override
    public char getTipus() {
        return 0;
    }

    @Override
    public int getFila() {
        return 0;
    }

    @Override
    public int getColumna() {
        return 0;
    }

    @Override
    public void setPosicion(int fila, int columna) throws RuntimeException {

    }

    @Override
    public boolean fiJoc() {
        return false;
    }
}
