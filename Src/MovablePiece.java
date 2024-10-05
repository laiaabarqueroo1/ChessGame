package Src;

import static java.lang.Character.getType;

public class MovablePiece extends Piece {

    public MovablePiece(char Type, int Line, char Column) {
        super(Type, Line, Column);
    }

    public boolean isMoveValid(int newLine, char newColumn) {
        int lineDifference = Math.abs(newLine - this.getRow());
        int columnDifference = Math.abs(newColumn - this.getColumn());

        switch (this.getTypes()) {
            case 'P':
                return newColumn == this.getColumn() && (newLine == this.getRow() + 1 || newLine == this.getRow() + 2);
            case 'B':
                return lineDifference == columnDifference;
            case 'R':
                return newLine == this.getRow() || newColumn == this.getColumn();
            case 'Q':
                return lineDifference == columnDifference || newLine == this.getRow() || newColumn == this.getColumn();
            case 'N':
                return (lineDifference == 2 && columnDifference == 1) || (lineDifference == 1 && columnDifference == 2);
            case 'K':
                return lineDifference <= 1 && columnDifference <= 1;
            default:
                return false;
        }
    }
}
