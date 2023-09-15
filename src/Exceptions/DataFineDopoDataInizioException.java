package Exceptions;

public class DataFineDopoDataInizioException extends Exception{
    public DataFineDopoDataInizioException(){
        super("La data di fine è precedente alla data di inizio");
    }
}
