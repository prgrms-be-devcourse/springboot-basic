package org.prgrms.vouchermanagement.console.io;

import org.prgrms.vouchermanagement.domain.Voucher;

import java.util.List;

public interface Output {

    void printMenu();

    void printVouchers(List<Voucher> vouchers);

    void printMenuError();
}
