package org.prgrms.kdtspringorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherRepositoryImpl implements VoucherRepository{
    List<Voucher> voucherList;
    public VoucherRepositoryImpl() {
        voucherList = new ArrayList<>();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        for (Voucher voucher : voucherList) {
            if (voucher.getVoucherId() == voucherId) {
                return Optional.of(voucher);
            }
        }
        return Optional.empty();
    }

    @Override
    public void insert(Optional<Voucher> voucher) {
        if (voucher.isPresent()) {
            voucherList.add(voucher.get());
        }
    }

    @Override
    public List<Voucher> getAllVoucher() {
        return voucherList;
    }
}
