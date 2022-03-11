package itmo.efarinov.soa.exceptions;

public class NestedRequestException extends Exception {
    public NestedRequestException(String message){
        super(message);
    }
}
