package prgms.vouchermanagementapp.voucher;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.voucher.model.Voucher;

import java.util.*;

@Component
public class MemoryVouchers {

    private static final Map<UUID, Voucher> vouchers = new HashMap<>();

    public void store(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
    }

    public List<Voucher> getVouchers() {
        return new ArrayList<>(vouchers.values());
    }
}
