package prgms.vouchermanagementapp.voucher.warehouse;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.voucher.model.Voucher;
import prgms.vouchermanagementapp.voucher.warehouse.model.MemoryVoucherRecord;
import prgms.vouchermanagementapp.voucher.warehouse.model.VoucherRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Profile("local")
public class MemoryVouchers implements VoucherWarehouse {

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
