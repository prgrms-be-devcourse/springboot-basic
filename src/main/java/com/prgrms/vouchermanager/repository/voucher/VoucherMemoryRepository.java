package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("dev")
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers = new HashMap<>();

    public Voucher create(Voucher voucher) {

        vouchers.put(voucher.getId(), voucher);
        return voucher;
    }

    public List<Voucher> list() {
        return vouchers
                .values()
                .stream()
                .toList();
    }

    @Override
    public Voucher findById(UUID id) {
        return null;
    }

    @Override
    public void updateDiscount(UUID id, int discount) {

    }

    @Override
    public UUID delete(UUID id) {
        return null;
    }
}
