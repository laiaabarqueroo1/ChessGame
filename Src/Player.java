package Src;

import java.util.ArrayList;
import java.util.List;

public class Player<E extends TypePiece> {
    private String name;
    private ArrayList<E> alivePieces;
    private List<E> deadPieces;

    // Constructor para inicializar el jugador con un conjunto de piezas vivas
    public Player(String name, List<E> initialPieces) {
        this.name = name;
        this.alivePieces = new ArrayList<>(initialPieces);
        this.deadPieces = new ArrayList<>();
    }

    public ArrayList<E> getAlivePieces() {
        return alivePieces;
    }

    public List<E> getDeadPieces() {
        return deadPieces;
    }

    public String getName() {
        return name;
    }

    public boolean movePiece(String from, String to, boolean isWhiteTurn) {
        // Convertir las posiciones de cadena a coordenadas
        int[] source = convertPosition(from);
        int[] destination = convertPosition(to);

        // Verificar si la conversión fue exitosa
        if (source == null || destination == null) {
            System.out.println("Invalid position format.");
            return false;
        }

        int previousRow = source[0];
        int previousColumn = source[1];
        int newRow = destination[0];
        int newColumn = destination[1];

        // Buscar la pieza en la posición anterior
        E piece = searchAtPosition(previousRow, previousColumn);

        // Verificar si se encontró una pieza
        if (piece != null) {
            // Código para mover la pieza aquí...
            char[][] board = Board.getBoard();

            // Validar que la posición de destino no está ocupada por una pieza del mismo color
            if (board[newRow][newColumn] != ' ' &&
                    Character.isLowerCase(board[newRow][newColumn]) == Character.isLowerCase(piece.getTypes())) {
                System.out.println("Invalid move: destination occupied by your own piece.");
                return false;
            }

            // Mover la pieza
            board[newRow][newColumn] = piece.getTypes();
            board[previousRow][previousColumn] = ' '; // Limpiar la posición anterior

            // Actualizar la posición de la pieza
            piece.setPosicion(newRow, newColumn); // Asegúrate de que el método setPosicion() actualiza tanto fila como columna

            System.out.println("Moved piece to (" + (char) (newColumn + 'A') + ", " + (8 - newRow) + ")");
            return true; // Retorna true si el movimiento fue exitoso
        } else {
            System.out.println("No piece found at specified position.");
        }

        return false; // Retorna false si no se pudo mover la pieza
    }



    // Remove a piece from a specific position
    public boolean removePieceAtPosition(int column, int row) throws FinishGameExcepcion {
        E piece = searchAtPosition(row, column);
        if (piece != null) {
            // Comprobar si la pieza es del mismo jugador
            if (!alivePieces.contains(piece)) {
                printError("You cannot remove your own piece at (" + column + ", " + row + ").");
                return false; // No se puede eliminar una pieza del mismo jugador
            }

            // Verificar si la pieza es el rey y lanzar excepción si es necesario
            if (piece.finishGame()) {
                throw new FinishGameExcepcion();
            }

            // Eliminar la pieza de las piezas vivas y agregarla a las piezas muertas
            alivePieces.remove(piece); // Eliminar la pieza viva
            deadPieces.add(piece);      // Añadir a las piezas muertas

            // Actualizar conteos de piezas vivas y muertas
            printAlivePiecesCount(); // Imprimir el conteo de piezas vivas
            printDeadPiecesCount();  // Imprimir el conteo de piezas muertas

            printSuccess(String.format("Removed piece from (%d, %d)", column, row));
            return true; // Retornar true si se eliminó correctamente
        } else {
            printError(String.format("No piece found at (%d, %d)", column, row));
            return false; // Retornar false si no se encontró la pieza
        }
    }



    public E searchAtPosition(int row, int column) {
        printInfo(String.format("Searching for piece at row: %d, column: %d", row, column));

        for (E piece : alivePieces) {
            int pieceColumnIndex = piece.getColumn() - 'A'; // Assuming piece.getColumn() is a char

            // Compare row and converted column
            if (piece.getRow() == row && pieceColumnIndex == column) {
                printSuccess(String.format("Piece found at position: (%d, %d)", row, column));
                return piece;
            }
        }

        printError("No piece found at specified position.");
        return null;
    }

    public int[] convertPosition(String position) {
        if (position.length() != 2) {
            printError("Invalid position format: " + position);
            return null;
        }

        char column = position.charAt(0); // 'A' a 'H'
        char row = position.charAt(1); // '1' a '8'

        // Validate that the column is between 'A' and 'H'
        if (column < 'A' || column > 'H') {
            printError("Invalid column: " + column);
            return null;
        }

        // Validate that the row is between '1' and '8'
        if (row < '1' || row > '8') {
            printError("Invalid row: " + row);
            return null;
        }

        // Adjust conversion:
        int x = 8 - Character.getNumericValue(row); // Row should be 0 to 7
        int y = column - 'A'; // Convert column 'A'-'H' to 0-7

        printInfo(String.format("Converted position %s to coordinates: (%d, %d)", position, x, y));

        // Return position as {row, column}
        return new int[]{x, y};
    }

    private void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    private void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public void printAlivePiecesCount() {
        System.out.println("\n" + name + "'s Alive Pieces:");
        System.out.println("=====================");
        System.out.println("Count: " + alivePieces.size());
        for (E piece : alivePieces) {
            System.out.print(piece.getTypes() + " " );
        }
        System.out.println("\n");
    }

    public void printDeadPiecesCount() {
        System.out.println("\n" + name + "'s Dead Pieces:");
        System.out.println("=====================");
        System.out.println("Count: " + deadPieces.size());
        for (E piece : deadPieces) {
            System.out.print(piece.getTypes() + " " );
        }
        System.out.println("\n");
    }
}
