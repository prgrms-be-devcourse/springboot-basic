package org.prgrms.kdtspringhomework.voucher.repository;

import org.prgrms.kdtspringhomework.voucher.domain.Voucher;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("test")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> Vouchers = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(Vouchers.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(Vouchers.values());
    }

    @Override
    public Voucher add(Voucher voucher) {
        Vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
