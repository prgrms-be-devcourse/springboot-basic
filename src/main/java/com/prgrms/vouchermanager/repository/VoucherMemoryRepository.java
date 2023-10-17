package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile("dev")
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers = new HashMap<>();

    public void create(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
    }

    public List<Voucher> list() {
        return vouchers
                .values()
                .stream()
                .toList();
    }
}
