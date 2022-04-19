package org.prgrms.kdt.domain.voucher.repository;

import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    UUID save(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByCustomerId(UUID customerId);

    List<Voucher> findAll();

    int updateById(Voucher voucher);

    int updateCustomerId(UUID voucherId, UUID customerId);

    int deleteById(UUID voucherId);

    int deleteAll();

    int deleteByCustomerId(UUID customerId);

    List<Voucher> findByVoucherTypeAndDate(VoucherType voucherType, LocalDate date);
}
