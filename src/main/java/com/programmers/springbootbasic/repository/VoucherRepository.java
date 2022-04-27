package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucherDTO);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAvailableVouchers();

    List<Voucher> findAll();

    void deleteById(UUID voucherId);

    void deleteAll();

}
