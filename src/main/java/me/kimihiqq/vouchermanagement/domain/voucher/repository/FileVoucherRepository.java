package me.kimihiqq.vouchermanagement.domain.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Profile("file")
@Repository
public class FileVoucherRepository implements VoucherRepository {

    private final String filePath;

    public FileVoucherRepository(@Value("${voucher.file.path}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Voucher save(Voucher voucher) {
        String voucherData = voucher.getVoucherId() + "," + voucher.getType() + "," + voucher.getDiscount() + System.lineSeparator();
        try {
            Files.write(Paths.get(filePath), voucherData.getBytes(), StandardOpenOption.APPEND);
            log.info("Voucher saved: {}", voucher.getVoucherId());
        } catch (IOException e) {
            log.error("Error saving voucher: {}", voucher.getVoucherId(), e);
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return findAll().stream()
                .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    VoucherTypeOption voucherTypeOption = VoucherTypeOption.valueOf(parts[1].toUpperCase());
                    long discount = Long.parseLong(parts[2]);
                    Voucher voucher = voucherTypeOption.createVoucher(discount);
                    vouchers.add(voucher);
                } catch (Exception e) {
                    log.warn("Invalid voucher found in storage: {}, error: {}", line, e.getMessage());
                }
            }
            log.info("All vouchers loaded");
        } catch (IOException e) {
            log.error("Error loading vouchers", e);
        }
        return vouchers;
    }

    @Override
    public void deleteById(UUID voucherId) {
        List<Voucher> vouchers = findAll();
        vouchers.removeIf(voucher -> voucher.getVoucherId().equals(voucherId));
        List<String> voucherDataList = vouchers.stream()
                .map(voucher -> voucher.getVoucherId() + "," + voucher.getType() + "," + voucher.getDiscount())
                .collect(Collectors.toList());
        try {
            Files.write(Paths.get(filePath), voucherDataList, StandardOpenOption.TRUNCATE_EXISTING);
            log.info("Voucher deleted: {}", voucherId);
        } catch (IOException e) {
            log.error("Error deleting voucher: {}", voucherId, e);
        }
    }

    @Override
    public List<Voucher> findAllByCreationDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public List<Voucher> findAllByType(VoucherTypeOption type) {
        return null;
    }


}