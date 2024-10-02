import Src.TypePiece;

import javax.sound.sampled.Line;
import java.lang.reflect.Type;

public class Piece implements TypePiece {
    private char Pawn = 'P'; // peon
    private char Bishop = 'B'; //alfil
    private char Queen = 'Q';
    private char King = 'K';
    private char Rook = 'R'; //torre
    private char Knight = 'K'; //caballos

    private char Column;
    private int Line;
    private char Type;

    private int Position;

    public Piece(char Type, int Line, char Column) {
        this.Type = Type;
        this.Line = Line;
        this.Column = Column;
        checkType(Type);
    }

    public char getTypes() {
        return Type;
    }


    public int getRow() {
        return Line;
    }

    @Override
    public int getColumn() {
        return Column;
    }

    @Override
    public void setPosicion(int row, int column) throws RuntimeException {

    }

    @Override
    public boolean finishGame() {
        return Type == King;
    }

    private void checkType(char type) {
        try {
            if (type != Pawn && type != Bishop && type != Queen &&
                    type != King && type != Rook && type != Knight) {
                throw new IllegalArgumentException("Invalid piece type: " + type);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }


    public String toString() {
        return "Piece{" +
                "type=" + Type +
                ", line=" + Line +
                ", column=" + Column +
                '}';
    }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        Piece piece = (Piece) o;
        return Type == piece.Type && Line == piece.Line && Column == piece.Column;
    }



}