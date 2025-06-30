package api.v1.exceptions;

public class SelectException extends Exception{
    public SelectException(){
        super();
    }
    public SelectException(String message){
        super(message);
    }
    public SelectException(String message, Throwable cause){
        super(message, cause);
    }
}
