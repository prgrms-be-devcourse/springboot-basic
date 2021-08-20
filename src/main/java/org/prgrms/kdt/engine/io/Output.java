package org.prgrms.kdt.engine.io;

import org.prgrms.kdt.engine.voucher.Voucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface Output {
    void help();
    void showVoucherOptions();
    void inputError();
    void createVoucher(Voucher voucher);
    void listVoucher(Map<UUID, Voucher> voucherList);
    void voucherListError();
}
