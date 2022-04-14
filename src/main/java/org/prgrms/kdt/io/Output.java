package org.prgrms.kdt.io;

public interface Output {

    void printFunctions();

    void printInputFunctionError();

    void printVoucherType();

    void printVoucherListEmptyError();

    void printExitMessage();

    void illegalArgumentExceptionMessage(IllegalArgumentException e);
}
