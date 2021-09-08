package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository {
    private static final Path FILE_PATH = Paths.get(System.getProperty("user.dir"), "src/main/resources/voucher.csv");
    private static final String DELIMITER = ",";
    private static final int INDEX_TYPE = 0;
    private static final int INDEX_UUID = 1;
    private static final int INDEX_AMOUNT = 2;
    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @PostConstruct
    public static void fileRead() {
        fileExistCheck();

        try {
            final List<String> lines = Files.readAllLines(FILE_PATH);
            for (final var line : lines) {
                final String[] voucherText = line.split(DELIMITER);
                final String voucherType = voucherText[INDEX_TYPE];
                final UUID voucherId = UUID.fromString(voucherText[INDEX_UUID]);
                final long discountAmount = Long.parseLong(voucherText[INDEX_AMOUNT]);

                if (voucherType.equals("FixedAmountVoucher")) {
                    storage.put(voucherId, new FixedAmountVoucher(voucherId, discountAmount));
                } else if (voucherType.equals("PercentDiscountVoucher")) {
                    storage.put(voucherId, new PercentDiscountVoucher(voucherId, discountAmount));
                }
            }
            System.out.println("File Read Complete!");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public static void fileWrite() {
        fileExistCheck();

        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH.toFile()))) {
            final StringBuffer sb = new StringBuffer();
            for (final Voucher voucher : storage.values()) {
                sb.append(voucher.getVoucherType());
                sb.append(",");
                sb.append(voucher.getVoucherId());
                sb.append(",");
                sb.append(voucher.getVoucherDiscount());
                sb.append(System.lineSeparator());
                bufferedWriter.write(String.valueOf(sb));
                sb.setLength(0);
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            System.out.println("File Write Complete!");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    static void fileExistCheck() {
        if (!Files.exists(FILE_PATH)) {
            createFile();
        }
    }

    static void createFile() {
        try {
            Files.createFile(FILE_PATH);
            System.out.println("create file");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Voucher> findById(final UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher insert(final Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
