package org.prgrms.kdt.io;

import java.util.List;

public interface Output {

    void printFunctions();

    void printVoucherType();

    void printList(List<?> list);

    void printMessage(String message);
}
