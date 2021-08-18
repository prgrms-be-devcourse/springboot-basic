package org.prgrms.kdt.engine.io;

import org.prgrms.kdt.engine.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface Output {
    void help();
    void inputError();
    void createVoucher(Voucher voucher);
    void listVoucher(Optional<List<Voucher>> voucherList);
}
