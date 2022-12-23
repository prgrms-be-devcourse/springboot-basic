package org.prgrms.vouchermanagement.io;

import java.util.List;

public interface Output {
    void printCommandNotices();

    void printSelectVoucherType();

    void printSelectVoucherDiscountAmount();

    void printVoucherCreateMessage();

    void printList(List<?> list);

    void printCustomerName();

    void printCustomerEmail();

    void printDeleteVoucherMessage();

    void printVoucherId();
}
