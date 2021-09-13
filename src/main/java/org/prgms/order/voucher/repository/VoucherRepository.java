package org.prgms.order.voucher.repository;

import org.prgms.order.voucher.entity.Voucher;
import org.prgms.order.voucher.entity.VoucherIndexType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findByType(VoucherIndexType Type);
    List<Voucher> findByTypeAmount(VoucherIndexType Type, long amount);
    List<Voucher> findAvailables();
    List<Voucher> findAll();

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);
    void deleteAll();

    void updateExpiryDate(UUID voucherId, LocalDateTime withNano);
}
