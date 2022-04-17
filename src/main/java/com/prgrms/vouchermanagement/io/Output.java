package com.prgrms.vouchermanagement.io;

import java.util.List;

public interface Output {
    void printMenu();

    void printMessage(String errorMessage);

    <T> void printList(List<T> list);
}
