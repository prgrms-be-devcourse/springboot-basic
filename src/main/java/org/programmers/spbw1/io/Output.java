package org.programmers.spbw1.io;

import org.programmers.spbw1.voucher.Voucher;

public interface Output {
    void initSelect();

    void showVoucherInfo(Voucher voucher);
}
