package org.prgrms.voucherapp.engine.voucher.repository;

import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.global.enums.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);

    List<Voucher> findByFilter(Optional<VoucherType> voucherType, Optional<LocalDateTime> after, Optional<LocalDateTime> before);
}
