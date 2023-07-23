package com.prgrms.spring.repository.voucher;

import com.prgrms.spring.domain.voucher.Voucher;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    List<Voucher> findByDiscountUnit(String discountUnit);

    void deleteVoucher(Voucher voucher);
}
