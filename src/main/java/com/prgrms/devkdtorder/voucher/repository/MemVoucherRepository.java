package com.prgrms.devkdtorder.voucher.repository;

import com.prgrms.devkdtorder.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> map = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(map.get(voucherId));
    }

    @Override
    public void save(Voucher voucher) {
        if (map.containsKey(voucher.getVoucherId())) {
            throw new RuntimeException("중복된 아이디의 바우처가 이미 존재합니다.");
        }
        map.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(map.values());
    }
}
