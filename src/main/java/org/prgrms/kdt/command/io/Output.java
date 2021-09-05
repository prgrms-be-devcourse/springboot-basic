package org.prgrms.kdt.command.io;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;

import java.util.List;

public interface Output {
    void printOnStart();

    void printOnExit();

    void printVoucherList(List<Voucher> vouchers);

    void printRequestVoucherType();

    void printRequestVoucherValue(VoucherType type);

    void printInputError();
}
