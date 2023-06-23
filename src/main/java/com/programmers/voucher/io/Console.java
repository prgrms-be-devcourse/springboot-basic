package com.programmers.voucher.io;

import com.programmers.voucher.enumtype.ConsoleCommandType;

public interface Console {
    ConsoleCommandType inputInitialCommand();

    void printCommandSet();

    String input(String hint);

    Integer intInput(String hint);

    void print(String result);

    void exit();
}
