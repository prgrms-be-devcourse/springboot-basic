package org.prgrms.kdt.engine.io;

public interface Output {
    // INFO
    String HELP = """
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            Type blacklist to list all black consumers.""";
    String INPUT = "명령어를 입력하세요";
    String INPUT_VOUCHER_NUM = """
            원하는 종류의 voucher 번호를 입력하세요.
            1. FixedAmountVoucher
            2. PercentDiscountVoucher""";
    String INPUT_DISCOUNT = "원하는 할인금액 또는 할인율을 입력해주세요.";

    // ERROR
    String CREATE_VOUCHER_ERROR = "voucher create error";
    String INPUT_ERROR = "input error";
    String NO_VOUCHER = "there is no voucher";
    String NO_BLACKLIST = "there is no blacklist";

    void logInfo(String output);
    void logError(String output);
    void printConsole(String output);
}
