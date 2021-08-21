package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class MemoryVoucherRepository implements VoucherRepository{

    List<Voucher> voucherList = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void insert(Voucher voucher) {
        voucherList.add(voucher);
    }

    @Override
    public List<Voucher> getVoucherList() {
        return voucherList;
    }
}
