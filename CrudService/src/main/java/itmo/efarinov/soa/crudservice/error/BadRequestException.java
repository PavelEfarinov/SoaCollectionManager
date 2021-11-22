package itmo.efarinov.soa.crudservice.error;

public class BadRequestException extends Exception {
    public BadRequestException(String s) {
        super(s);
    }
}
