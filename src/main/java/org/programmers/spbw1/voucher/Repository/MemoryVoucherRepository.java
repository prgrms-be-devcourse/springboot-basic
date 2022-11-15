package org.programmers.spbw1.voucher.Repository;

import org.programmers.spbw1.Console;
import org.programmers.spbw1.io.Output;
import org.programmers.spbw1.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.UUID;

@Repository
// @Profile("JDBC")
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final Output output = new Console();

    @Override
    public Optional<Voucher> getVoucherById(UUID id) {
        if(storage.containsKey(id))
            return Optional.of(storage.get(id));
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherID(), voucher);
        return voucher;
    }

    @Override
    public int getStoredVoucherNum() {
        return storage.size();
    }

    @Override
    public void clear() {
        this.storage.clear();
    }

    @Override
    public void showAllVouchers() {
        storage.forEach((id, voucher) -> {
            output.showVoucherInfo(voucher);
        });
    }
}
