package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    void update(Voucher voucher);

    void deleteAll();

    void deleteById(UUID voucherId);

    List<Voucher> findAllByCreatedAtAndVoucherType(LocalDateTime createdAt, VoucherType voucherType);
}
