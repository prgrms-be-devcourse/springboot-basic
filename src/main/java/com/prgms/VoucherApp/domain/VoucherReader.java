package com.prgms.VoucherApp.domain;

import com.prgms.VoucherApp.storage.VoucherStorage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherReader {

    private final VoucherStorage voucherStorage;

    public VoucherReader(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public Optional<Voucher> readOne(UUID voucherId) {
        return voucherStorage.findByVoucherId(voucherId);
    }

    public List<Voucher> readVoucherList() {
        return voucherStorage.findAll();
    }
}
