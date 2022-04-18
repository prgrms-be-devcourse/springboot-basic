package org.prgrms.kdt.io;

import java.util.List;

public interface Output {

    void printFunctions();

    void printInputFunctionError();

    void printVoucherType();

    void printVoucherListEmptyError();

    void printExitMessage();

    void printExceptionMessage(String exceptionMessage);

    void printList(List<String> blackList);
}
