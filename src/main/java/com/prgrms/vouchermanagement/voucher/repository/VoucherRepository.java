package com.prgrms.vouchermanagement.voucher.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Long save(Voucher voucher);

    List<Voucher> findAll();

    List<Voucher> findByType(VoucherType voucherType);

    List<Voucher> findByPeriod(LocalDateTime from, LocalDateTime end);

    List<Voucher> findVoucherByCustomer(Long customerId);

    Optional<Voucher> findById(Long voucherId);

    void update(Voucher voucher);

    void remove(Long voucherId);
}
