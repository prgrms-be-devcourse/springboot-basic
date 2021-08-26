package org.prgrms.kdt.io;

import org.prgrms.kdt.domain.voucher.Voucher;

public interface Output {

    void printVoucher(Voucher voucher);

    void newLine();
}
