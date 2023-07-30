package org.prgrms.kdt.voucher.dao;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll(VoucherType voucherType);

    void deleteById(UUID id);

    List<Voucher> findByType(VoucherType type);
}
