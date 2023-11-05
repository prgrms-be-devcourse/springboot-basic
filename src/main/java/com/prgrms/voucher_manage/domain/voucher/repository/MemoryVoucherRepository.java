package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.base.ErrorMessage;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.prgrms.voucher_manage.base.ErrorMessage.*;


@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> getAll() {
        return storage.values().stream().toList();
    }

    @Override
    public List<Voucher> getByType(VoucherType type){
        List<Voucher> vouchers = storage.values().stream().toList();
        return vouchers.stream().
                filter(voucher -> voucher.getType() == type)
                .collect(Collectors.toList());
    }

    @Override
    public Voucher getById(UUID voucherId) {
        Voucher voucher = storage.get(voucherId);
        if (voucher==null)
            throw new RuntimeException(VOUCHER_NOT_EXISTS.getMessage());
        return voucher;
    }

    @Override
    public void update(Voucher voucher) {
        storage.put(voucher.getId(), voucher);
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }
}
