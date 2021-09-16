package org.prgrms.kdtspringorder;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
public class VoucherRepositoryImpl implements VoucherRepository {
    Map<UUID, Voucher> storage;

    public VoucherRepositoryImpl() {
        this.storage = new HashMap<UUID, Voucher>();
    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public void insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Map<UUID, Voucher> getAllVoucher() {
        return storage;
    }
}
