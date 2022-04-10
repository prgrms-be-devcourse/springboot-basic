package com.prgrms.vouchermanagement.io;

import java.util.InputMismatchException;

public interface Input {
    String inputCommand();

    int inputVoucherType() throws InputMismatchException;

    int inputDiscount() throws InputMismatchException;
}
