package Src;

public interface TypePiece {
    // mètodes abstractes: aquí detallem les capçaleres,
// les classes que implementen la interfície, hauran d'implementar aquests mètodes
    public abstract char getTypes();
    public abstract int getRow();
    public abstract int getColumn();
    public abstract void setPosicion(int row, int column) throws RuntimeException;
    // si la posició no és correcte cal llançar una excepció
    public abstract boolean fiJoc() ;
}
