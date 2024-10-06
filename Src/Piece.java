package Src;

import Src.TypePiece;

import javax.sound.sampled.Line;
import java.lang.reflect.Type;

public class Piece implements TypePiece {
    protected  static  final char Pawn = 'P'; // peon
    protected  static final  char Bishop = 'B'; //alfil
    protected static   final char Queen = 'Q';
    protected static final  char King = 'K';
    protected static final char Rook = 'R'; //torre
    protected static  final  char Knight = 'N'; //caballos

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
        this.Column = (char) (column + 'A');
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
    // Método para validar movimientos
    public boolean isMoveValid(int newLine, char newColumn, boolean isWhiteTurn) {
        // Obtener la diferencia de filas y columnas
        int lineDifference =  this.Line - newLine;
        int columnDifference = this.Column - newColumn;

        // Verificar que la nueva columna es válida
        if (newColumn < 'A' || newColumn > 'H') {
            return false; // Columna inválida
        }

        switch (this.getTypes()) {
            case Pawn: // Peón
                if (isWhiteTurn) { // Movimiento para pieza blanca
                    if (columnDifference == 0) {
                        // Movimiento hacia adelante
                        if (lineDifference == 1) {
                            return true; // Un movimiento hacia adelante
                        }
                        // Movimiento inicial: puede avanzar 2 casillas
                        if (this.Line == 2 && lineDifference == 2) {
                            return true; // Primer movimiento del peón
                        }
                    } else if (Math.abs(columnDifference) == 1 && lineDifference == 1) {
                        return true; // Captura en diagonal
                    }
                } else { // Movimiento para pieza negra
                    if (columnDifference == 0) {
                        // Movimiento hacia adelante
                        if (lineDifference == -1) {
                            return true; // Un movimiento hacia adelante
                        }
                        // Movimiento inicial: puede avanzar 2 casillas
                        if (this.Line == 7 && lineDifference == -2) {
                            return true; // Primer movimiento del peón
                        }
                    } else if (Math.abs(columnDifference) == 1 && lineDifference == -1) {
                        return true; // Captura en diagonal
                    }
                }
                break;
            case Bishop: // Alfil
                return Math.abs(lineDifference) == Math.abs(columnDifference); // Movimiento diagonal
            case Rook: // Torre
                return (newLine == this.Line || newColumn == this.Column); // Movimiento vertical u horizontal
            case Queen: // Reina
                return (Math.abs(lineDifference) == Math.abs(columnDifference) || newLine == this.Line || newColumn == this.Column);
            case Knight: // Caballo
                return (Math.abs(lineDifference) == 2 && Math.abs(columnDifference) == 1) || (Math.abs(lineDifference) == 1 && Math.abs(columnDifference) == 2);
            case King: // Rey
                return Math.abs(lineDifference) <= 1 && Math.abs(columnDifference) <= 1; // Una casilla en cualquier dirección
        }

        return false; // Si no coincide con ninguna pieza
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