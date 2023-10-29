package com.prgrms.springbasic.io;

import com.prgrms.springbasic.common.MenuType;

import java.util.UUID;

public interface Input {
    String inputMenuType();
    String inputCommandType(MenuType menuType);
    String inputDiscountType();
    long inputLong(ConsoleMessage consoleMessage);
    long inputPercentValue();
    String inputString(ConsoleMessage consoleMessage);
    String inputEmail();
    UUID inputUUID(ConsoleMessage consoleMessage);
}
