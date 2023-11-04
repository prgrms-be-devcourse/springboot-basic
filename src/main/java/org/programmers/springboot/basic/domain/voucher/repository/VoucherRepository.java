package org.programmers.springboot.basic.domain.voucher.repository;

import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void save(Voucher voucher);
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> find(UUID voucherId, VoucherType voucherType, Long discount, LocalDateTime createdAt);
    Optional<Voucher> findByTypeNDiscount(VoucherType voucherType, Long discount);
    List<Voucher> findAll();
    void update(Voucher voucher);
    void delete(UUID voucherId);
    void deleteAll();

}
