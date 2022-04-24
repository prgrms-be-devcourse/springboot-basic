package com.prgrms.vouchermanagement.io;

import java.util.InputMismatchException;
import java.util.UUID;

public interface Input {
    String inputCommand();

    int inputVoucherType() throws InputMismatchException;

    String inputString(String title);

    UUID inputUUID(String title) throws InputMismatchException;

    int inputNumber(String title) throws InputMismatchException;
}
