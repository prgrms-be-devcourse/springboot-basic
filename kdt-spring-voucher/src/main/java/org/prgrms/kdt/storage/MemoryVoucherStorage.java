package org.prgrms.kdt.storage;

import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryVoucherStorage implements VoucherStorage {
    private final static List<Voucher> memoryVoucherDatabase = new ArrayList<>();

    @Override
    public void saveVoucher(Voucher newVoucher) {
        memoryVoucherList.add(newVoucher);
    }

    @Override
    public List<Voucher> findAllVoucher() {
        return memoryVoucherDatabase;
    }
}
