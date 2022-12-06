package com.programmers.assignment.voucher.engine.repository;

import com.programmers.assignment.voucher.engine.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    List<Voucher> findByDiscountWay(String discountWay);

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    void deleteAll();

}
