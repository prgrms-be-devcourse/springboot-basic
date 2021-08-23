package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 바우처 데이터에 대한 CRUD를 담당합니다. at Memory
 */
@Repository
@Profile({"dev", "default"})
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
