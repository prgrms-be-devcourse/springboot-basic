package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.utils.CsvFileUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private static final String CSV_SEPARATOR = ",";

    private final String csvFilePath;
    private final Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    public FileVoucherRepository(@Value("${csv.file.voucher.path}") String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @PostConstruct
    public void init() {
        readFile();
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(vouchers.get(id));
    }

    @Override
    public Optional<Voucher> findByName(String name) {
        return vouchers.values().stream()
                .filter(voucher -> voucher.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Voucher> findByNameLike(String name) {
        return vouchers.values().stream()
                .filter(voucher -> voucher.containsName(name))
                .toList();
    }

    @Override
    public List<Voucher> findByTypeAndDates(String voucherType, LocalDate startDate, LocalDate endDate) {
        Stream<Voucher> voucherStream = vouchers.values().stream();
        if (voucherType != null && !voucherType.isBlank()) {
            voucherStream = voucherStream.filter(voucher -> voucherType.equals(voucher.getVoucherType().toString()));
        }
        if (startDate != null && endDate != null) {
            voucherStream = voucherStream.filter(voucher ->
                    !voucher.getCreatedAt().isBefore(startDate.atStartOfDay()) &&
                            !voucher.getCreatedAt().isAfter(endDate.atTime(23, 59, 59))
            );
        }
        return voucherStream.toList();
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
        updateFile();
        return voucher;
    }

    @Override
    public int delete(UUID id) {
        Voucher voucher = vouchers.remove(id);
        updateFile();
        return voucher != null ? 1 : 0;
    }

    public void readFile() {
        final List<String> lines = CsvFileUtil.readCsvFile(csvFilePath);
        lines.forEach(line -> {
            String[] voucherInfo = line.split(CSV_SEPARATOR);
            Voucher voucher = VoucherFactory.createVoucher(voucherInfo);
            vouchers.put(voucher.getId(), voucher);
        });
    }

    private void updateFile() {
        CsvFileUtil.updateCsvFile(csvFilePath, vouchers.values().stream().toList());
    }
}
