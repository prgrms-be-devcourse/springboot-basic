package com.prgms.VoucherApp.storage;

import com.prgms.VoucherApp.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherStorage {

    Optional<Voucher> findByVoucherId(UUID voucherId);

    List<Voucher> findAll();

    Voucher save(Voucher voucher);
}
