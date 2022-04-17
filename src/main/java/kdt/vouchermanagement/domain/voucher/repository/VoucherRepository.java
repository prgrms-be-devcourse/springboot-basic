package kdt.vouchermanagement.domain.voucher.repository;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Optional<Voucher> findByVoucherTypeAndDiscountValue(VoucherType voucherType, String discountValue);
}
