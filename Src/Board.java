package Src;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private static char[][] board;
    private static ArrayList<Piece> whitePieces;
    private static ArrayList<Piece> blackPieces;

    public static char[][] getBoard() {
        return board;
    }

    public static ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public static ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

    public static void initializeBoard() {
        board = new char[8][8];
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();

        initializeWhitePieces();
        initializeBlackPieces();

        // Fill empty squares
        for (int i = 2; i < 6; i++) {
            Arrays.fill(board[i], ' '); // Empty squares
        }
    }

    public static void initializeWhitePieces() {
        for (int i = 0; i < 8; i++) {
            Piece pawn = new Piece(Piece.Pawn, 6, (char) ('A' + i));
            whitePieces.add(pawn);
            board[6][i] = pawn.getTypes();
        }

        char[] majorPiecesWhite = {Piece.Rook, Piece.Knight, Piece.Bishop, Piece.Queen, Piece.King, Piece.Bishop, Piece.Knight, Piece.Rook};
        for (int i = 0; i < majorPiecesWhite.length; i++) {
            Piece piece = new Piece(majorPiecesWhite[i], 7, (char) ('A' + i));
            whitePieces.add(piece);
            board[7][i] = piece.getTypes();
        }
    }

    public static void initializeBlackPieces() {
        for (int i = 0; i < 8; i++) {
            Piece pawn = new Piece(Piece.Pawn, 1, (char) ('A' + i));
            blackPieces.add(pawn);
            board[1][i] = Character.toLowerCase(pawn.getTypes());
        }

        char[] majorPiecesBlack = {Piece.Rook, Piece.Knight, Piece.Bishop, Piece.Queen, Piece.King, Piece.Bishop, Piece.Knight, Piece.Rook};
        for (int i = 0; i < majorPiecesBlack.length; i++) {
            Piece piece = new Piece(majorPiecesBlack[i], 0, (char) ('A' + i));
            blackPieces.add(piece);
            board[0][i] = Character.toLowerCase(piece.getTypes());
        }
    }

    public static void showBoard() {
        System.out.println("  A B C D E F G H");
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(8 - i);
        }
        System.out.println("  A B C D E F G H");
    }

    public static void main(String[] args) {
        initializeBoard();
        showBoard();
    }
}
