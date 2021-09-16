package org.prgrms.devcourse.repository;

import org.prgrms.devcourse.domain.Voucher;
import org.prgrms.devcourse.util.VoucherFileReader;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private final ConcurrentHashMap<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
    private static final String VOUCHER_DATA_SOURCE = "data/voucher.csv";

    @Override
    public Voucher insert(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherMap.getOrDefault(voucherId, null));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherMap.values());
    }

    @PostConstruct
    public void postConstruct() {
        VoucherFileReader voucherFileReader = new VoucherFileReader();
        voucherMap.putAll(voucherFileReader.readFile(VOUCHER_DATA_SOURCE));
    }

    @PreDestroy
    public void preDestroy() {
        try {
            Files.deleteIfExists(Path.of(VOUCHER_DATA_SOURCE));
            Files.createFile(Path.of(VOUCHER_DATA_SOURCE));

            for (Voucher voucher : voucherMap.values())
                Files.writeString(Path.of(VOUCHER_DATA_SOURCE), voucher.toString(), StandardOpenOption.APPEND);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
