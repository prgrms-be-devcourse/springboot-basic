package org.prgrms.kdtspringdemo.io;

import java.util.List;

public interface Output {
    void printCommandList();

    void printStartAppInfo();

    void printCommandError(String command);

    void printCreateSelect();

    void printCreateCustomerByTypes();

    void printCreateVoucherByTypes();

    void printListSelect();

    void printObject(Object obj);

    <T> void printList(List<T> stream);

    void printDeleteSelect();

    void printDeleteVoucher();

    void printFindSelect();

    void printFindCustomer();

    void printFindVouchers();
}
