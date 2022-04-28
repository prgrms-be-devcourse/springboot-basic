package org.devcourse.voucher.view;


import org.devcourse.voucher.error.ErrorType;

import java.util.List;

public interface Output {
    void mainMenu();

    void info(String msg);

    void printList(List<Object> list);

    void createMenu();

    void listMenu();

    void discountMenu();

    void warn(ErrorType errorType);
}
