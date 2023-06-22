package kr.co.programmers.springbootbasic.io;


import kr.co.programmers.springbootbasic.voucher.VoucherType;

public interface Output {
    void printProgramMenu();

    void printCreationMenu();

    void printAmountEnterMessage(VoucherType voucherType);

    void printMessage(String message);

    void printExit();
}
