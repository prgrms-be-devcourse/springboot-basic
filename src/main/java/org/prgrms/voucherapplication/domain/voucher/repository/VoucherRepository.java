package org.prgrms.voucherapplication.domain.voucher.repository;

import org.prgrms.voucherapplication.domain.voucher.entity.Voucher;
import org.prgrms.voucherapplication.domain.voucher.entity.VoucherType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    int deleteAll();

    Optional<Voucher> findById(UUID voucherId);

    void deleteById(UUID voucherId);

    List<Voucher> findByType(VoucherType type);

    List<Voucher> findByTypeAndCreatedAt(VoucherType type, LocalDateTime createdAt);

    List<Voucher> findByCreatedAtAfter(LocalDateTime createdAt);
}
