package com.devcourse.springbootbasic.application.voucher.repository;

import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"dev"})
public class MemoryVoucherRepository implements VoucherRepository {

    private final VoucherMap voucherMap;

    public MemoryVoucherRepository() {
        this.voucherMap = new VoucherMap();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return voucherMap.addVoucher(voucher);
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return voucherMap.getAllVouchers();
    }

}
