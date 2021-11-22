package itmo.efarinov.soa.crudservice.error;

public class MaxPageNumberExceededException extends Exception {
    public MaxPageNumberExceededException(String s) {
        super(s);
    }
}
