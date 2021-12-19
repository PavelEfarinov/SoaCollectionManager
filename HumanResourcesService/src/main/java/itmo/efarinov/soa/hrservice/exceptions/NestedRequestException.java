package itmo.efarinov.soa.hrservice.exceptions;

public class NestedRequestException extends Exception {
    public NestedRequestException(String message){
        super(message);
    }
}
