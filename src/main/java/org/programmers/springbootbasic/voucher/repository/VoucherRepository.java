package org.programmers.springbootbasic.voucher.repository;

import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    void updateVoucherOwner(UUID voucherId, Long memberId);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByType(VoucherType type);

    List<Voucher> findAll();

    void remove(UUID voucherId);
}
