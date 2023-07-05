package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherStorage {

    Optional<Voucher> findByVoucherId(UUID voucherId);

    List<Voucher> findAll();

    void save(Voucher voucher);
}
