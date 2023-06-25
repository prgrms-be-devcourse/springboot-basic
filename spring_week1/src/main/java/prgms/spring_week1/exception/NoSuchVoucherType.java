package prgms.spring_week1.exception;

public class NoSuchVoucherType extends RuntimeException{
    public NoSuchVoucherType() {
    }

    public NoSuchVoucherType(String message) {
        super(message);
    }
}
