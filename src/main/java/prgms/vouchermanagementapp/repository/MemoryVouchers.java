package prgms.vouchermanagementapp.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.MemoryVoucherRecord;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.VoucherRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Profile("memory")
public class MemoryVouchers implements Vouchers {

    private static final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public void store(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public VoucherRecord getVoucherRecord() {
        return new MemoryVoucherRecord(new ArrayList<>(vouchers.values()));
    }
}
