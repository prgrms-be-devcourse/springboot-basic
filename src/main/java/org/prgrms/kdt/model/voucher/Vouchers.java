package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.io.OutputConsole;

import java.util.List;

public class Vouchers {
    private final List<Voucher> vouchers;

    public Vouchers(List<Voucher> vouchers) {
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
