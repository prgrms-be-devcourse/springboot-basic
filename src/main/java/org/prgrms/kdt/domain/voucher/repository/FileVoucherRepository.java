package org.prgrms.kdt.domain.voucher.repository;

import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.util.CsvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {
    @Value("${csv.voucher.path}")
    private String csvPath;
    @Value("${csv.voucher.file-name}")
    private String fileName;
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final int TYPE_INDEX = 0;
    private static final int ID_INDEX = 1;
    private static final int DISCOUNT_INDEX = 2;

    @Override
    public UUID save(Voucher voucher) {
        VoucherType voucherType = voucher.getVoucherType();
        String data = createCsvData(voucher, voucherType);
        try {
            CsvUtils.writeCsv(csvPath, fileName, data);
        } catch (IOException e) {
            logger.error("Save file voucher error, {}", e.getMessage());
            throw new IllegalArgumentException("파일의 경로 혹은 이름을 확인해주세요.");
        }
        return voucher.getVoucherId();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> vouchers;
        try {
            vouchers = parseCsvToList(CsvUtils.readCsv(csvPath, fileName));
        } catch (IOException e) {
            logger.error("Find file voucher Id error, {}", e.getMessage());
            throw new IllegalArgumentException("파일의 경로 혹은 이름을 확인해주세요.");
        }
        return vouchers.stream()
                .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                .findAny();
    }

    @Override
    public List<Voucher> findByCustomerId(UUID voucherId) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        List<List<String>> csvData;
        try {
            csvData = CsvUtils.readCsv(csvPath, fileName);
        } catch (IOException e) {
            logger.error("Find file all voucher error, {}", e.getMessage());
            throw new IllegalArgumentException("파일의 경로 혹은 이름을 확인해주세요.");
        }
        return parseCsvToList(csvData);
    }

    @Override
    public int updateById(Voucher voucher) {
        return 0;
    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteByCustomerId(UUID customerId) {

    }

    private String createCsvData(Voucher voucher, VoucherType voucherType) {
        StringBuilder data = new StringBuilder(VoucherType.getValue(voucherType))
                .append(",")
                .append(voucher.getVoucherId().toString())
                .append(",")
                .append(voucher.getDiscountValue());
        return data.toString();
    }

    private List<Voucher> parseCsvToList(List<List<String>> csvData) {
        List<Voucher> vouchers = new ArrayList<>();
        for (List<String> row : csvData) {
            VoucherType voucherType = VoucherType.findVoucherType(row.get(TYPE_INDEX));
            UUID voucherId = UUID.fromString(row.get(ID_INDEX));
            String discount = row.get(DISCOUNT_INDEX);
            LocalDateTime now = LocalDateTime.now();
            vouchers.add(new Voucher(voucherId, voucherType, Long.parseLong(discount), now, now));
        }
        return vouchers;
    }
}
