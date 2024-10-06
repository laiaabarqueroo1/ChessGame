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
    public boolean isMoveValid(int newLine, char newColumn, boolean isWhiteTurn) {
        // Obtener la diferencia de filas y columnas
        int lineDifference = this.Line - newLine;
        int columnDifference = this.Column - newColumn;

        // Verificar que la nueva columna es válida
        if (newColumn < 'A' || newColumn > 'H') {
            return false; // Columna inválida
        }

        // Verificar si la pieza está intentando moverse a su propia casilla
        if (newLine == this.Line && newColumn == this.Column) {
            return false; // No se puede mover a la misma casilla
        }

        switch (this.getTypes()) {
            case Pawn: // Peón
                // Movimiento hacia adelante
                if (columnDifference == 0) {
                    if (isWhiteTurn) {
                        // Movimiento blanco
                        if (lineDifference == 1) return true; // Un movimiento hacia adelante
                        if (this.Line == 2 && lineDifference == 2) return true; // Primer movimiento del peón
                    } else {
                        // Movimiento negro
                        if (lineDifference == -1) return true; // Un movimiento hacia adelante
                        if (this.Line == 7 && lineDifference == -2) return true; // Primer movimiento del peón
                    }
                }
                break;
            case Bishop: // Alfil
                return Math.abs(lineDifference) == Math.abs(columnDifference); // Movimiento diagonal
            case Rook: // Torre
                // Movimiento vertical u horizontal
                if (newLine == this.Line || newColumn == this.Column) {
                    return isPathClear(newLine, newColumn); // Comprobar si el camino está despejado
                }
                break;
            case Queen: // Reina
                return (Math.abs(lineDifference) == Math.abs(columnDifference) ||
                        newLine == this.Line || newColumn == this.Column);
            case Knight: // Caballo
                return (Math.abs(lineDifference) == 2 && Math.abs(columnDifference) == 1) ||
                        (Math.abs(lineDifference) == 1 && Math.abs(columnDifference) == 2);
            case King: // Rey
                return Math.abs(lineDifference) <= 1 && Math.abs(columnDifference) <= 1; // Una casilla en cualquier dirección
        }

        return false; // Si no coincide con ninguna pieza
    }

    private boolean isPathClear(int newLine, char newColumn) {
        char[][] board = Board.getBoard(); // Obtener el tablero
        int columnStart = this.Column - 'A'; // Índice de columna actual
        int columnEnd = newColumn - 'A'; // Índice de columna de destino

        // Si nos movemos verticalmente
        int rowStart = 0;
        if (columnStart == columnEnd) {
            rowStart = this.Line;
            int step = newLine > rowStart ? 1 : -1; // Determina la dirección
            for (int i = rowStart + step; i != newLine; i += step) {
                if (board[i][columnStart] != ' ') {
                    return false; // Hay una pieza en el camino
                }
            }
        }
        // Si nos movemos horizontalmente
        else if (rowStart == newLine) {
            int step = columnEnd > columnStart ? 1 : -1; // Determina la dirección
            for (int i = columnStart + step; i != columnEnd; i += step) {
                if (board[rowStart][i] != ' ') {
                    return false; // Hay una pieza en el camino
                }
            }
        }

        return true; // El camino está claro
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