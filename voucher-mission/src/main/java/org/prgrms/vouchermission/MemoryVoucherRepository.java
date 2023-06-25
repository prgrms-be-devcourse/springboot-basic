package org.prgrms.vouchermission;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher create(Voucher voucher) {
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
