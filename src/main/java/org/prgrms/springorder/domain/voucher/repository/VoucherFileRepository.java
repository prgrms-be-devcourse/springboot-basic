package org.prgrms.springorder.domain.voucher.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PreDestroy;
import org.prgrms.springorder.config.VoucherFileProperties;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
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
public class VoucherFileRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    private final File fileStore;

    public VoucherFileRepository(VoucherFileProperties properties) {
        fileStore = FileUtil.createFile(properties.getPath(),
            properties.getStoredName() + properties.getStoredExtension());
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

    @Override
    public Voucher update(Voucher voucher) {
        storage.remove(voucher.getVoucherId());
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Deprecated
    @Override
    public Optional<CustomerWithVoucher> findByIdWithCustomer(UUID voucherId) {
        throw new RuntimeException("지원되지 않는 기능입니다.");
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }

    @Deprecated
    @Override
    public List<Voucher> findAllBy(LocalDateTime startDate, LocalDateTime endDate,
        VoucherType voucherType) {
        throw new RuntimeException("지원되지 않는 기능입니다.");
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

        UUID customerId = UUID.fromString(split[3].trim());

        LocalDateTime createdAt = LocalDateTime.parse(split[4]);

        return VoucherFactory.toVoucher(voucherType, voucherId, amount, customerId, createdAt);
    }

    private String serialize(Voucher voucher) {
        return String.format("%s, %s, %s, %s, %s", voucher.getVoucherType(),
            voucher.getVoucherId(), voucher.getAmount(), voucher.getCustomerId(),
            voucher.getCreatedAt());
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
