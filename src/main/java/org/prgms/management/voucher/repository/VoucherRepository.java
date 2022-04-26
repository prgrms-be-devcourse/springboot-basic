package org.prgms.management.voucher.repository;

import org.prgms.management.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByCreatedAt(LocalDateTime cratedAt);

    List<Voucher> findByType(String type);

    Optional<Voucher> findByName(String name);

    Voucher update(Voucher voucher);

    Voucher delete(Voucher voucher);

    void deleteAll();
}
