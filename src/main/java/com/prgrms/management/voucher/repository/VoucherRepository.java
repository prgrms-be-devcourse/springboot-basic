package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    List<Voucher> findAllByVoucherTypeOrCreatedAt(VoucherType voucherType, LocalDate date);

    List<UUID> findCustomerByVoucherType(VoucherType voucherType);

    Optional<Voucher> findById(UUID voucherId);

    void updateByCustomerId(UUID voucherId, UUID customerId);

    void deleteById(UUID customerId);

    void deleteAll();

}
