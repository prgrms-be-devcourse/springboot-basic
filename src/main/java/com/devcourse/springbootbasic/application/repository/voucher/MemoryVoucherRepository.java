package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.domain.VoucherMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile({"dev"})
public class MemoryVoucherRepository implements VoucherRepository {

    private final VoucherMap voucherMap;

    public MemoryVoucherRepository() {
        this.voucherMap = new VoucherMap(new HashMap<>());
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        voucherMap.addVoucher(voucher);
        return Optional.of(voucher);
    }

    @Override
    public List<String> findAll() {
        return voucherMap.getAllVouchers();
    }

}
