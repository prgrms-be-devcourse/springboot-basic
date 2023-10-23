package devcourse.springbootbasic.exception;

public class VoucherException extends RuntimeException {

    private VoucherException(VoucherErrorMessage voucherErrorMessage) {
        super(voucherErrorMessage.getMessage());
    }

    public static VoucherException of(VoucherErrorMessage voucherErrorMessage) {
        return new VoucherException(voucherErrorMessage);
    }
}
