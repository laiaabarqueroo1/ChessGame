package Src;

import java.util.ArrayList;

public class PieceInitializer {

    public static ArrayList<TypePiece> initializeWhitePieces() {
        ArrayList<TypePiece> whitePieces = new ArrayList<>();

        // Agregar peones
        for (int i = 0; i < 8; i++) {
            whitePieces.add(new Piece(Piece.Pawn, 6, (char) ('a' + i))); // Peones en la fila 6
        }

        // Agregar piezas mayores
        char[] majorPieces = {Piece.Rook, Piece.Knight, Piece.Bishop, Piece.Queen, Piece.King, Piece.Bishop, Piece.Knight, Piece.Rook};
        for (int i = 0; i < majorPieces.length; i++) {
            whitePieces.add(new Piece(majorPieces[i], 7, (char) ('a' + i))); // Piezas en la fila 7
        }

        return whitePieces;
    }

    public static ArrayList<TypePiece> initializeBlackPieces() {
        ArrayList<TypePiece> blackPieces = new ArrayList<>();

        // Agregar peones
        for (int i = 0; i < 8; i++) {
            blackPieces.add(new Piece(Piece.Pawn, 1, (char) ('a' + i))); // Peones en la fila 1
        }

        // Agregar piezas mayores
        char[] majorPieces = {Piece.Rook, Piece.Knight, Piece.Bishop, Piece.Queen, Piece.King, Piece.Bishop, Piece.Knight, Piece.Rook};
        for (int i = 0; i < majorPieces.length; i++) {
            blackPieces.add(new Piece(majorPieces[i], 0, (char) ('a' + i))); // Piezas en la fila 0
        }

        return blackPieces;
    }
}
