package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherStorage {

    void save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findByVoucherId(UUID voucherId);

    List<Voucher> findByVoucherType(VoucherType type);

    void update(Voucher voucher);

    void deleteById(UUID id);
}
