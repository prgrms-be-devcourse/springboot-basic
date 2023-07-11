package com.devcourse.springbootbasic.application.voucher.repository;

import com.devcourse.springbootbasic.application.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    List<Voucher> findAllVouchers();

    Optional<Voucher> findById(UUID voucherId);

    void deleteAll();

    void deleteById(UUID voucherId);

}
