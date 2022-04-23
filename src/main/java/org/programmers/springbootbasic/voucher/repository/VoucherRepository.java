package org.programmers.springbootbasic.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.RateDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.programmers.springbootbasic.voucher.domain.VoucherType.FIXED;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.RATE;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    void remove(UUID voucherId);
}
