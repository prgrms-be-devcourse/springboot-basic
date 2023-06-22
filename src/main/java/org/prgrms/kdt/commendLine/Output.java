package org.prgrms.kdt.commendLine;

import org.prgrms.kdt.voucher.Voucher;

import java.util.List;

public interface Output {
    void printMenu();
    void printVoucherTypes();
    void printAllBoucher(List<Voucher> bouchers);
}
