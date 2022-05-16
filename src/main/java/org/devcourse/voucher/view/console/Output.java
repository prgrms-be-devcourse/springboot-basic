package org.devcourse.voucher.view.console;


import org.devcourse.voucher.error.ErrorType;

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
