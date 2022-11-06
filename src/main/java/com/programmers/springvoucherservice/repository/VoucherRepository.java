package com.programmers.springvoucherservice.repository;

import com.programmers.springvoucherservice.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository<T> {
    Optional<Voucher> findById(UUID id);

    List<Voucher> findAllVouchers();

    Voucher registerVoucher(Voucher voucher);
}
