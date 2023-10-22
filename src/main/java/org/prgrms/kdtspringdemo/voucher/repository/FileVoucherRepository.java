package org.prgrms.kdtspringdemo.voucher.repository;

import org.apache.commons.csv.CSVRecord;
import org.prgrms.kdtspringdemo.file.CsvFileHandler;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;

import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository{
    private CsvFileHandler csvFileHandler;
    private final String filePath = "src/main/resources/csvFiles/voucherList.csv";
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    public FileVoucherRepository() {
        this.csvFileHandler = new CsvFileHandler(filePath);
    }

    public void initCsvFileHandler(String filePath) {
        this.csvFileHandler = new CsvFileHandler(filePath);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        List<String[]> newData = new ArrayList<>();
        String voucherId = voucher.getVoucherId().toString();
        String voucherAmount = voucher.getAmount().toString();
        String voucherType = voucher.getVoucherType();
        newData.add(new String[]{voucherId, voucherAmount, voucherType});

        try {
            csvFileHandler.writeCSV(newData);
        }catch (IOException e) {
            logger.error(e.getMessage());
        }

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try{
            List<CSVRecord> data = csvFileHandler.readCSV();

            CSVRecord csvRecord = data.stream()
                    .filter(line -> UUID.fromString(line.get("voucherId")).equals(voucherId))
                    .findFirst()
                    .orElseThrow();

            VoucherTypeFunction voucherTypeFunction = VoucherTypeFunction.findByCode(csvRecord.get("voucherType"));
            return Optional.ofNullable(voucherTypeFunction
                    .create(UUID.fromString(csvRecord.get("voucherId")), Long.parseLong(csvRecord.get("amount"))));
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Map<UUID, Voucher>> getAllVouchers() {
        Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
        try {
            List<CSVRecord> data = csvFileHandler.readCSV();
            data.stream().forEach(line -> {
                UUID voucherId = UUID.fromString(line.get("voucherId"));
                Long amount = Long.parseLong(line.get("amount"));
                String voucherType = line.get("voucherType");

                VoucherTypeFunction voucherTypeFunction = VoucherTypeFunction.findByCode(voucherType);
                Voucher voucher = voucherTypeFunction.create(voucherId, amount);
                voucherMap.put(voucherId, voucher);
            });
            return Optional.of(voucherMap);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return Optional.of(voucherMap);
    }
}
