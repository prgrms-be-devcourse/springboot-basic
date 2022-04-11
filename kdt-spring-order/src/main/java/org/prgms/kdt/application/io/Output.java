package org.prgms.kdt.application.io;

import org.prgms.kdt.application.voucher.domain.Voucher;

import java.util.List;

public interface Output {


    void commandTypeMessage();
    void voucherTypeMessage();
    void printVoucherList(List<Voucher> voucherList);
    void printExit();
}
