package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher saveVoucher(Voucher voucher);
    List<Voucher> findAll();
    void updateVoucher(Voucher voucher);
    void deleteAll();
    void deleteById(UUID voucherId);
    Optional<Voucher> findVoucherById(UUID voucherId);
}
