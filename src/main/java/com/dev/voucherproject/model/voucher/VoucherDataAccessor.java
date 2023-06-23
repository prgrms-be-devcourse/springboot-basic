package com.dev.voucherproject.model.voucher;


import com.dev.voucherproject.storage.VoucherStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherDataAccessor {
    private final VoucherStorage voucherStorage;

    public VoucherDataAccessor(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public void insert(Voucher voucher) {
        voucherStorage.insert(voucher);
    }

    public List<Voucher> findAll() {
        return voucherStorage.findAll();
    }
}
