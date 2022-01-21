package itmo.efarinov.soa.hrservice.facade.exceptions;

public class NestedRequestException extends Exception {
    public NestedRequestException(String message){
        super(message);
    }
}
