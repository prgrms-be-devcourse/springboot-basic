package prgms.vouchermanagementapp.voucher;

import prgms.vouchermanagementapp.voucher.model.Voucher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryVouchers {

    private static final Map<UUID, Voucher> vouchers = new HashMap<>();

    public void store(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
    }
}
