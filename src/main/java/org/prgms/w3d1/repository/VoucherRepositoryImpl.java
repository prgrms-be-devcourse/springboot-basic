package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.voucher.Voucher;

import java.util.*;

public class VoucherRepositoryImpl implements VoucherRepository {

    private final HashMap<UUID, Voucher> voucherHashMap = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherHashMap.get(voucherId));
    }

    @Override
    public void save(Voucher voucher) {
        voucherHashMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherHashMap.values());
    }
}
