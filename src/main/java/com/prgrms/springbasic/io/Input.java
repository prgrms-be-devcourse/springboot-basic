package com.prgrms.springbasic.io;

import java.util.UUID;

public interface Input {
    String inputMenuType();
    String inputDiscountType();
    long inputLong(ConsoleMessage consoleMessage);
    long inputPercentValue();
    String inputString(ConsoleMessage consoleMessage);
    String inputEmail();
    UUID inputUUID(ConsoleMessage consoleMessage);
}
