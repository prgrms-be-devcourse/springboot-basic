package org.prgrms.kdt.shop.repository;

import java.util.Date;
import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.enums.VoucherType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    void deleteAll();

    void delete(UUID voucherId);

    List<Voucher> findByType(VoucherType voucherType);
}
