package itmo.efarinov.soa.crudservice.filter.error;

public class MaxPageNumberExceededException extends Exception {
    public MaxPageNumberExceededException(String s) {
        super(s);
    }
}
