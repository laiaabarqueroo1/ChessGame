package Src;

import Src.TypePiece;

import javax.sound.sampled.Line;
import java.lang.reflect.Type;

public class Piece implements TypePiece {
    protected  static char Pawn = 'P'; // peon
    protected  static char Bishop = 'B'; //alfil
    protected static  char Queen = 'Q';
    protected static  char King = 'K';
    protected static  char Rook = 'R'; //torre
    protected static  char Knight = 'N'; //caballos

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
        this.Line = row;
        this.Column = (char) column;
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


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Piece)) return false;

        Piece piece = (Piece) obj;


        return this.Type == piece.Type &&
                this.Line == piece.Line &&
                this.Column == piece.Column;
    }

}