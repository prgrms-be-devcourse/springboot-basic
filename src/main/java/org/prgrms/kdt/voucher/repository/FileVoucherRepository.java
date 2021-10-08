package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
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
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final Path FILE_PATH = Paths.get(System.getProperty("user.dir"), "src/main/resources/voucher.csv");
    private static final String DELIMITER = ",";
    private static final int INDEX_TYPE = 0;
    private static final int INDEX_UUID = 1;
    private static final int INDEX_AMOUNT = 2;
    private static final Map<UUID, Voucher> STORAGE = new ConcurrentHashMap<>();

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

                STORAGE.put(voucherId, VoucherFactory.create(voucherType, voucherId, discountAmount));
            }
            System.out.println("Voucher File Read Complete!");
        } catch (final IOException e) {
            logger.error("{}", e.getMessage());
        }
    }

    @PreDestroy
    public static void fileWrite() {
        fileExistCheck();

        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH.toFile()))) {
            final StringBuffer sb = new StringBuffer();
            for (final Voucher voucher : STORAGE.values()) {
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
            System.out.println("File Write Complete!");
        } catch (final IOException e) {
            logger.error("{}", e.getMessage());
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
            logger.error("{}", e.getMessage());
        }
    }

    @Override
    public Voucher insert(final Voucher voucher) {
        STORAGE.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(final UUID voucherId) {
        return Optional.ofNullable(STORAGE.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(STORAGE.values());
    }

    @Override
    public Voucher updateEmail(final Voucher voucher, final String email) {
        // JDBC만 구현
        return voucher;
    }

    @Override
    public Optional<List<Voucher>> findByEmail(final String email) {
        // JDBC만 구현
        return Optional.empty();
    }

    @Override
    public void delete(final UUID voucherId) {
        // JDBC만 구현
    }

    @Override
    public List<Customer> findCustomer(final UUID voucherId) {
        // JDBC만 구현
        return null;
    }
}
