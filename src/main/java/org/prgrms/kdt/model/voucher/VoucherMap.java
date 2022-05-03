package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.io.OutputConsole;

import java.util.Map;
import java.util.UUID;

public class VoucherMap {

    private final Map<UUID, Voucher> vouchers;

    public VoucherMap(Map<UUID, Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public boolean isEmptyMap() {
        return vouchers.isEmpty();
    }

    public void printKeys() {
        for (Map.Entry<UUID, Voucher> entry : vouchers.entrySet()) {
            OutputConsole.printMessage(entry.getKey().toString());
        }
    }

    public int size() {
        return vouchers.size();
    }

    public Map<UUID, Voucher> getVouchers() {
        return Map.copyOf(vouchers);
    }
}
