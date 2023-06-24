package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findVoucher(UUID voucherId);
    UUID saveVoucher(Voucher voucher);

}
