package org.prgrms.kdt.command.io;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;

import java.util.List;

public interface Output {
    void init();

    void exit();

    void voucherList(List<Voucher> vouchers);

    void inputVoucherType();

    void inputVoucherValue(VoucherType type);
}
