package org.prgrms.springorder.domain.voucher.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PreDestroy;
import org.prgrms.springorder.config.VoucherFileProperties;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.service.VoucherFactory;
import org.prgrms.springorder.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    private final File fileStore;

    public FileVoucherRepository(VoucherFileProperties properties) {
        fileStore = FileUtil.createFile(properties.getPath(), properties.getStoredName() + properties.getStoredExtension());
        readAll();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.putIfAbsent(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream()
            .toList();
    }

    @Override
    public void deleteAll() {
        FileUtil.deleteFile(fileStore);
        storage.clear();
    }

    private void readAll() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileStore))) {

            String line = "";
            while (((line = bufferedReader.readLine()) != null)) {

                Voucher voucher = deserialize(line);
                storage.put(voucher.getVoucherId(), voucher);
            }

        } catch (IOException e) {
            logger.warn("file read Error. {}", e.getMessage());
            throw new RuntimeException("file i/o error", e);
        }
    }

    private Voucher deserialize(String data) {
        String[] split = data.split(",");

        VoucherType voucherType = VoucherType.of(split[0].trim());

        UUID voucherId = UUID.fromString(split[1].trim());

        long amount = Long.parseLong(split[2].trim());

        return VoucherFactory.toVoucher(voucherType, voucherId, amount);
    }

    private String serialize(Voucher voucher) {
        return String.format("%s, %s, %s", voucher.getVoucherType(),
            voucher.getVoucherId(), voucher.getAmount());
    }

    private void writeAll() {
        String allDate = this.storage.values()
            .stream()
            .map(this::serialize)
            .collect(Collectors.joining(System.lineSeparator()));

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileStore, false))) {
            bufferedWriter.write(allDate);
            bufferedWriter.newLine();
        } catch (IOException e) {
            logger.warn("insert file error {}, {}", e.getMessage(), e.getClass().getName(), e);
            throw new RuntimeException("file i/o error", e);
        }
    }

    @PreDestroy
    private void preDestroy() {
        writeAll();
    }

}
