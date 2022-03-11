package itmo.efarinov.soa.crud.filter.error;

public class MaxPageNumberExceededException extends Exception {
    public MaxPageNumberExceededException(String s) {
        super(s);
    }
}
