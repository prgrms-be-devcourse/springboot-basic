package org.programmers.spbw1.voucher;

import org.programmers.spbw1.Console;
import org.programmers.spbw1.io.Output;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("JDBC")
public class JDBCVoucherRepository implements VoucherRepository{
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
