package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.Voucher;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher findVoucher(UUID voucherId);

    UUID saveVoucher(Voucher voucher);

    Collection<Voucher> getAllVouchers();

}
