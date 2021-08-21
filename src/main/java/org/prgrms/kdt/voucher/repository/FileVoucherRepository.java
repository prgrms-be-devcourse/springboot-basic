package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File Type: CSV
 * - VOUCHER_ID, VOUCHER_TYPE, POLICY_VALUE \n
 */
@Repository
@Qualifier("file")// 용도를 구별
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileVoucherRepository implements VoucherRepository {

    private final Path filePath = Paths.get(System.getProperty("user.dir"), "voucher", "voucher.csv");
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    public FileVoucherRepository() {
        loadStorage();
    }

    private void loadStorage() {
        if(!Files.exists(filePath))
            return;

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                List<String> voucherInfo = Arrays.asList(line.split(","));

                if (FixedAmountVoucher.class.getSimpleName().equals(voucherInfo.get(0))) {
                    Voucher parsedVoucher = new FixedAmountVoucher(UUID.fromString(voucherInfo.get(1)), Long.parseLong(voucherInfo.get(2)));
                    storage.put(parsedVoucher.getVoucherId(), parsedVoucher);
                } else if (PercentDiscountVoucher.class.getSimpleName().equals(voucherInfo.get(0))) {
                    Voucher parsedVoucher = new PercentDiscountVoucher(UUID.fromString(voucherInfo.get(1)), Long.parseLong(voucherInfo.get(2)));
                    storage.put(parsedVoucher.getVoucherId(), parsedVoucher);
                } else {
                    throw new IllegalArgumentException("Not match any Voucher Class");
                }
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile(Voucher voucher) {
        // 1. if not exist? -> create dir & file
        // 2. else append
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileWriter writer = new FileWriter(filePath.toFile(), true)) {
            String voucherInfo = voucher.toString() + '\n';
            writer.write(voucherInfo);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> find() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        saveFile(voucher);
        return voucher;
    }
}
