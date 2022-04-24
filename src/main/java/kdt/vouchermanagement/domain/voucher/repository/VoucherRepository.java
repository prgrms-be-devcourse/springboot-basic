package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;

import java.util.Optional;

public interface VoucherRepository {

    Optional<Voucher> findByVoucherTypeAndDiscountValue(VoucherType voucherType, int discountValue);

    Voucher save(Voucher voucher);
}
