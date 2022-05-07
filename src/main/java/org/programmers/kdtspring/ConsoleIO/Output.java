package org.programmers.kdtspring.ConsoleIO;

import java.util.UUID;

public interface Output {

    void showAllVoucher();

    void terminate();

    void vouchersBelongToCustomer(UUID customerId);

    void voucherCreated();

    void showBlackList();
}
