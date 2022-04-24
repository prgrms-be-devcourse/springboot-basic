package com.prgrms.vouchermanagement.io;

import java.util.List;

public interface Output {
    void printMenu();

    void printMessage(String errorMessage);

    void printWalletMenu();

    <T> void printList(List<T> list);
}
