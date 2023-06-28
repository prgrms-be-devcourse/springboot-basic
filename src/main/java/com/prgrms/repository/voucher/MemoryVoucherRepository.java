package com.prgrms.repository.voucher;

import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherList;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Primary
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new TreeMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public VoucherList getAllVoucherList() {
        return new VoucherList(storage.values().stream()
                .collect(Collectors.toList()));
    }

}
