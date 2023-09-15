package Exceptions;

public class NullFieldException extends Exception{
    public NullFieldException(){
        super("Uno o più campi obbligatori sono nulli");
    }
}
