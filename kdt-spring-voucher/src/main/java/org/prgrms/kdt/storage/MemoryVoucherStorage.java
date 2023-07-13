package org.prgrms.kdt.storage;

import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Profile("default")
public class MemoryVoucherStorage implements VoucherStorage {
    private final static List<Voucher> memoryVoucherList = new ArrayList<>();

    @Override
    public void saveVoucher(Voucher newVoucher) {
        memoryVoucherList.add(newVoucher);
    }

    @Override
    public List<Voucher> findAllVoucher() {
        return Collections.unmodifiableList(memoryVoucherList);
    }
}
