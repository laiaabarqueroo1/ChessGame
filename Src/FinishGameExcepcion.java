package Src;


public class FinishGameExcepcion extends Exception {

    // Default constructor with a custom message
    public FinishGameExcepcion() {
        super("The king has been captured! Game over.");
    }

}
