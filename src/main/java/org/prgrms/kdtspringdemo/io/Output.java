package org.prgrms.kdtspringdemo.io;

import java.util.List;
import java.util.stream.Stream;

public interface Output {
    void printCommandList();

    void printStartAppInfo();

    void printCommandError(String command);

    void printCreateSelect();

    void printCreateCustomerByTypes();

    void printCreateVoucherByTypes();

    void printListSelect();

    <T> void printList(List<T> stream);
}
