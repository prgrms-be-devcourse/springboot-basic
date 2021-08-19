package org.prgrms.kdt.voucher;

import org.prgrms.kdt.engine.OpenCsv;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("csv")
public class CsvVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    OpenCsv csvWriter = new OpenCsv();

    public void save(String filePath) throws IOException {
        csvWriter.saveToFile(storage, filePath);
    }

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
    public Map<UUID, Voucher> getStorage() {
        return storage;
    }
}
