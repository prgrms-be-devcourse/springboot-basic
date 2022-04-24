package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }
}
