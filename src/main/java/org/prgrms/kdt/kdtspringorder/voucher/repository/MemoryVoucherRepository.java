package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryVoucherRepository implements VoucherRepository{

    private Map<UUID,Voucher> voucherMap = new HashMap<>();

    @Override
    public List<Voucher> findAll() {
        return voucherMap.values().stream()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherMap.get(voucherId));
    }

    @Override
    public void insert(Voucher voucher) {
        this.voucherMap.put(voucher.getVoucherId(), voucher);
    }

}
