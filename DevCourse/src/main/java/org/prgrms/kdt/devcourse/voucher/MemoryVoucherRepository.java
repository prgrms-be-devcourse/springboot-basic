package org.prgrms.kdt.devcourse.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MemoryVoucherRepository implements VoucherRepository {
    private List<Voucher> voucherList = new ArrayList<>();
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return voucherList.stream()
                .filter(v -> v.getVoucherId() == voucherId)
                .findFirst();
    }

    @Override
    public Voucher save(Voucher voucher) {
        voucherList.add(voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherList;
    }
}
