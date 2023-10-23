package com.prgrms.springbasic.io;

public interface Input {
    String inputMenuType();
    String inputDiscountType();
    long inputLong(ConsoleMessage consoleMessage);
    long inputPercentValue();
    String inputString(ConsoleMessage consoleMessage);
    String inputEmail();
}
