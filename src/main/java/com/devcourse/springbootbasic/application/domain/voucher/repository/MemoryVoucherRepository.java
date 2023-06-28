package com.devcourse.springbootbasic.application.domain.voucher.repository;

import com.devcourse.springbootbasic.application.domain.voucher.data.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile({"dev"})
public class MemoryVoucherRepository implements VoucherRepository {

    private Map<UUID, Voucher> voucherMap;

    public MemoryVoucherRepository() {
        this.voucherMap = new HashMap<>();
    }

    public MemoryVoucherRepository(Map<UUID, Voucher> voucherMap) {
        this.voucherMap = voucherMap;
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        Map<UUID, Voucher> temp = voucherMap;

        temp.put(voucher.getVoucherId(), voucher);
        voucherMap = temp;

        return Optional.of(voucher);
    }

    @Override
    public List<String> findAll() {
        return voucherMap.values()
                .stream()
                .map(Voucher::toString)
                .toList();
    }

}
