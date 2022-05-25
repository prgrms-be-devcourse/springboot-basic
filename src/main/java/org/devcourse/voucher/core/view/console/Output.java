package org.devcourse.voucher.core.view.console;


import org.devcourse.voucher.core.exception.ErrorType;

import java.util.List;

public interface Output {
    void mainMenu();

    void info(String msg);

    void printList(List<?> list);

    void createMenu();

    void listMenu();

    void discountMenu();

    void voucherMenu();

    void nameMenu();

    void emailMenu();

    void error(ErrorType errorType);

}
