package org.programmers.kdtspringvoucherweek1.voucher.repository;

import org.programmers.kdtspringvoucherweek1.io.Output;
import org.programmers.kdtspringvoucherweek1.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> store = new LinkedHashMap<>();
    private final Output output;

    @Autowired
    public MemoryVoucherRepository(Output output) {
        this.output = output;
    }

    @Override
    public void save(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void findByIdAll() {
        store.forEach((uuid, voucher) -> output.msg(voucher.toString()));
    }
}
