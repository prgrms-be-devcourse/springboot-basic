package org.programmers.springboot.basic.domain.voucher.repository;

import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void add(Voucher voucher);
    Optional<Voucher> get(UUID voucherId);
    Optional<Voucher> findByTypeNDiscount(VoucherType voucherType, Long discount);
    List<Voucher> getAll();
    void update(Voucher voucher);
    void delete(UUID voucherId);
    void deleteAll();
}
