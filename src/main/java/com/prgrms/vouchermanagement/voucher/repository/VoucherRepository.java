package com.prgrms.vouchermanagement.voucher.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();

    List<Voucher> findByType(VoucherType voucherType);

    List<Voucher> findByPeriod(LocalDateTime from, LocalDateTime end);

    Optional<Voucher> findById(UUID voucherId);

    void update(Voucher voucher);

    void remove(UUID voucherId);
}
