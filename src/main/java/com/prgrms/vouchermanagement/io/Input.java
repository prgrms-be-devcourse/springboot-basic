package com.prgrms.vouchermanagement.io;

import java.util.InputMismatchException;

public interface Input {
    String inputCommand();

    int inputVoucherType() throws InputMismatchException;

    String inputString(String title);

    Long inputId(String title) throws InputMismatchException;

    int inputNumber(String title) throws InputMismatchException;
}
