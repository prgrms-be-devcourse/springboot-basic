package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.Voucher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class MemVoucherRepository implements VoucherRepository, InitializingBean {

    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Map<UUID, Voucher> findAllVoucher() {
        return storage;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("VoucherRepository:" + this.getClass().getCanonicalName());
    }
}
