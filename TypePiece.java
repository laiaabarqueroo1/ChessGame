public interface TypePiece {
    // mètodes abstractes: aquí detallem les capçaleres,
// les classes que implementen la interfície, hauran d'implementar aquests mètodes
    public abstract char getTipus();
    public abstract int getFila();
    public abstract int getColumna();
    public abstract void setPosicion(int fila, int columna) throws RuntimeException;
    // si la posició no és correcte cal llançar una excepció
    public abstract boolean fiJoc() ;
}
