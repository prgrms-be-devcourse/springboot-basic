package org.prgrms.kdt.engine.voucher.repository;

import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.prgrms.kdt.engine.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private final static String DELIMITER = "&";
    private final static String VOUCHERS_PATH = "src/main/resources/vouchers/";
    private final static Map<UUID, Voucher> tmpStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        return Optional.ofNullable(tmpStorage.get(voucherId));
    }

    @Override
    public Optional<Map<UUID, Voucher>> getAll() {
        if (tmpStorage.isEmpty())
            return Optional.empty();
        return Optional.of(tmpStorage);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            Path filePath = Paths.get(VOUCHERS_PATH + voucher);
            Files.createFile(filePath);
            tmpStorage.put(voucher.getVoucherId(), voucher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public void setCustomerId(UUID voucherId, UUID customerId) {
        // 미구현
    }

    @Override
    public Optional<Map<UUID, Voucher>> getCustomerVoucher(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public void deleteCustomerVoucher(UUID customerId) {

    }

    @Override
    public void deleteVoucher(UUID voucherId) {
        // 미구현
    }

    @Override
    public Optional<UUID> findCustomerByVoucher(UUID voucherId) {
        return Optional.empty();
    }

    private Map.Entry<UUID, Voucher> deserializeVoucher(String filename) {
        String[] name = filename.split(DELIMITER);
        VoucherType type = VoucherType.valueOf(name[0]);
        long rate = Long.parseLong(name[2]);

        UUID voucherId = UUID.fromString(name[1]);
        Voucher voucher = type.createVoucher(rate);
        return new AbstractMap.SimpleEntry(voucherId, voucher);
    }

    @PostConstruct
    private void loadVouchers() {
        File voucherDir = new File(VOUCHERS_PATH);
        File[] vouchers = voucherDir.listFiles((file) -> !file.isHidden());
        if (vouchers == null) return;

        for (File voucher : vouchers) {
            Map.Entry<UUID, Voucher> entry = deserializeVoucher(voucher.getName());
            tmpStorage.put(entry.getKey(), entry.getValue());
        }
    }
}


