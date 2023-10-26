package org.prgrms.kdtspringdemo.voucher.repository;

import org.apache.commons.csv.CSVRecord;
import org.prgrms.kdtspringdemo.file.CsvFileHandler;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;

import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
@Profile("dev")
@PropertySource(value = "classpath:application_prod.yml")
public class FileVoucherRepository implements VoucherRepository{
    private CsvFileHandler csvFileHandler;
    @Value("${voucher_file}")
    private String filePath;
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    public FileVoucherRepository() {
        this.csvFileHandler = new CsvFileHandler();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        List<String[]> newData = new ArrayList<>();
        String voucherId = voucher.getVoucherId().toString();
        VoucherPolicy voucherPolicy = voucher.getVoucherPolicy();
        String discountAmount = String.valueOf(voucherPolicy.getAmount());
        String voucherType = voucherPolicy.getVoucherType();
        newData.add(new String[]{voucherId, discountAmount, voucherType});

        try {
            csvFileHandler.writeCSV(filePath, newData);
        }catch (IOException e) {
            logger.error(e.getMessage());
        }

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try{
            List<CSVRecord> data = csvFileHandler.readCSV(filePath);

            CSVRecord csvRecord = data.stream()
                    .filter(line -> UUID.fromString(line.get("voucherId")).equals(voucherId))
                    .findFirst()
                    .orElseThrow();

            VoucherTypeFunction voucherTypeFunction = VoucherTypeFunction.findByCode(csvRecord.get("voucherType"));
            return Optional.ofNullable(voucherTypeFunction
                    .create(UUID.fromString(csvRecord.get("voucherId")),Long.parseLong(csvRecord.get("amount"))));
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<List<Voucher>> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        try {
            List<CSVRecord> data = csvFileHandler.readCSV(filePath);
            data.stream().forEach(line -> {
                UUID voucherId = UUID.fromString(line.get("voucherId"));
                Long amount = Long.parseLong(line.get("amount"));
                String voucherType = line.get("voucherType");

                VoucherTypeFunction voucherTypeFunction = VoucherTypeFunction.findByCode(voucherType);
                Voucher voucher = voucherTypeFunction.create(voucherId, amount);
                voucherList.add(voucher);
            });
            return Optional.of(voucherList);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return Optional.of(voucherList);
    }
}
