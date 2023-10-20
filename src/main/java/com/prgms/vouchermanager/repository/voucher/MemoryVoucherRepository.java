package com.prgms.vouchermanager.repository.voucher;

import com.prgms.vouchermanager.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID,Voucher>vouchers;

    public MemoryVoucherRepository() {
        this.vouchers = new ConcurrentHashMap<>();
    }

    @Override
    public void create(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
    }
    @Override
    public List<Voucher> getAllVouchers() {
        List<Voucher> voucherList = new ArrayList<>(vouchers.values());
        return voucherList;
    }
}
