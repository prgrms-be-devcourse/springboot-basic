package org.prgrms.kdt.repo;

import org.prgrms.kdt.configure.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherMemoryRepo implements VoucherRepository{

    private List<Voucher> voucherList = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        //양자 분해 해서 찾는다.
        Optional<Voucher> voucher = voucherList.stream()
                .filter(v -> v.getVoucherId() == voucherId)
                .findFirst();
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherList;
    }

    @Override
    public void save(Voucher voucher) {
        voucherList.add(voucher);
    }
}
