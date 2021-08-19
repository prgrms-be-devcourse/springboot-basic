package org.prgrms.kdt.io;

import org.prgrms.kdt.strategy.Voucher;

public interface Output {

    void printVoucher(Voucher voucher);

    void newLine();
}
