package org.prgms.io;

public interface InOut {
    void optionMessage();

    String input();

    void inputError(String inputText);

    int chooseVoucher();
}
