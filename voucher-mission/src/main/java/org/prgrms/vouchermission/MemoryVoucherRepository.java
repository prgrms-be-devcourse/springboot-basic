package org.prgrms.vouchermission;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        ArrayList<Voucher> voucherList = new ArrayList<>();

        Collection<Voucher> vouchers = storage.values();
        for (Voucher voucher : vouchers) {
            voucherList.add(voucher);
        }
        return voucherList;
    }
}
