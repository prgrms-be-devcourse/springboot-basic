package prgms.vouchermanagementapp.storage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.storage.model.MemoryVoucherRecord;
import prgms.vouchermanagementapp.storage.model.VoucherRecord;

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
