package com.devcourse.springbootbasic.application.repository;

import com.devcourse.springbootbasic.application.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile({"dev"})
public class MemoryVoucherRepository implements VoucherRepository {

    private Map<UUID, Voucher> voucherMap = new HashMap<>();

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
        return Optional.of(voucher);
    }

    @Override
    public List<String> findAll() {
        return voucherMap.values()
                .stream()
                .map(Voucher::toString)
                .toList();
    }

    @Override
    public void setVoucherMap(Map<UUID, Voucher> voucherMap) {
        this.voucherMap = voucherMap;
    }
}
