package com.prgms.missionW3D2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherRepositoryImpl implements VoucherRepository {
    List<Voucher> voucherList = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> voucherList() {
        return voucherList;
    }

    @Override
    public void insert(Voucher voucher) {
        voucherList.add(voucher);
    }
}
