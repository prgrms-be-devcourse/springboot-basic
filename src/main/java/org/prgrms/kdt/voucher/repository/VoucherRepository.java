package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// 대부분 Repository는 interface를 가짐
public interface VoucherRepository {

    // 없을 수도 있으니까 Optional<>
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> find();
    Voucher insert(Voucher voucher);
}
