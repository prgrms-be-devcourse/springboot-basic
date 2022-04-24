package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    @Override
    public Optional<Voucher> findByVoucherTypeAndDiscountValue(VoucherType voucherType, int discountValue) {
        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }
}
