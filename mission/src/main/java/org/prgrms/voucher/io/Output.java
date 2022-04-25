package org.prgrms.voucher.io;

public interface Output {

    void printPrompt();

    void printInvalidInputError();

    void printVoucherType();

    void printVoucherDiscountType();

    void printMessage(String response);
}
