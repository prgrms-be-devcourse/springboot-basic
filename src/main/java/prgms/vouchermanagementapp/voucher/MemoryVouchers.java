package prgms.vouchermanagementapp.voucher;

import prgms.vouchermanagementapp.voucher.model.Voucher;

import java.util.*;

public class MemoryVouchers {

    private static final Map<UUID, Voucher> vouchers = new HashMap<>();

    public void store(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
    }

    public List<Voucher> getVouchers() {
        return new ArrayList<>(vouchers.values());
    }
}
