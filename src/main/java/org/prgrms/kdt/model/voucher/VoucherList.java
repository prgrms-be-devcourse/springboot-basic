package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.io.OutputConsole;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VoucherList {
    private final List<Voucher> vouchers;

    public VoucherList(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public boolean isEmptyList() {
        return vouchers.isEmpty();
    }

    public void printList() {
        vouchers.forEach(voucher -> {
            OutputConsole.printMessage(voucher.getVoucherId().toString());
        });
    }

    public List<Voucher> getVouchers() {
        return List.copyOf(vouchers);
    }
}
