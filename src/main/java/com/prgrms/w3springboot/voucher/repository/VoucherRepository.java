package com.prgrms.w3springboot.voucher.repository;

import com.prgrms.w3springboot.voucher.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

}
