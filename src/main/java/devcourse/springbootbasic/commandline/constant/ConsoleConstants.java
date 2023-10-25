package devcourse.springbootbasic.commandline.constant;

public final class ConsoleConstants {

    public static final String VOUCHER_PROGRAM_START_MESSAGE = "Voucher Program";
    public static final String VOUCHER_CREATE_MESSAGE = "Voucher ID [%s] Created.";
    public static final String CUSTOMER_CREATE_MESSAGE = "Customer ID [%s] Created.";
    public static final String CUSTOMER_UPDATE_MESSAGE = "Customer ID [%s] Updated.";
    public static final String VOUCHER_UPDATE_MESSAGE = "Voucher ID [%s] Updated.";
    public static final String VOUCHER_DELETE_MESSAGE = "Voucher ID [%s] Deleted.";

    private ConsoleConstants() {
        throw new AssertionError();
    }
}
