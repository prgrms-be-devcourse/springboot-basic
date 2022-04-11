package org.prgrms.voucherprgrms.io;

import org.prgrms.voucherprgrms.voucher.model.Voucher;

import java.util.List;

public interface OutputConsole {
    void voucherList(List<Voucher> list);
    void commandErrorMessage();
}
