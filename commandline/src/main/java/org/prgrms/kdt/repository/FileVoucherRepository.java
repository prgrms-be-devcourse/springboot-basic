package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.io.CSVInOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final CSVInOut csvIO;

    public FileVoucherRepository(@Value("${voucher.path}") String path) {
        this.csvIO = new CSVInOut(path);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        csvIO.writeCSV(voucher);
        storage.put(UUID.randomUUID(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }
}
