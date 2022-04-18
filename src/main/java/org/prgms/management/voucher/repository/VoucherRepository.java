package org.prgms.management.voucher.repository;

import org.prgms.management.customer.entity.Customer;
import org.prgms.management.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public interface VoucherRepository {
    Optional<Voucher> insert(Voucher voucher);

    Optional<Voucher> update(Voucher voucher);

    Map<UUID, Voucher> getAll();

    Optional<Voucher> getById(UUID voucherId);

    Optional<Voucher> getByCreatedAt(LocalDateTime cratedAt);

    Optional<Voucher> getByType(String type);

    Optional<Voucher> delete(UUID voucherId);

    void deleteAll();
}
