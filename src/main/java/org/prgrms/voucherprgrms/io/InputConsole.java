package org.prgrms.voucherprgrms.io;

public interface InputConsole {
    String commandInput();

    long getVoucherValue();

    String getVoucherType();

    int getSelect();
}
