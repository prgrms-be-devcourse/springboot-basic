package com.prgmrs.voucher.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.prgmrs.voucher.exception.FileNotReadException;
import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.PercentDiscountVoucher;
import com.prgmrs.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Profile("!dev")
public class FileVoucherDatabase implements VoucherDatabase {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherDatabase.class);

    @Override
    public Map<UUID, Voucher> load(String filePath) {
        boolean append = Files.exists(Paths.get(filePath));
        Map<UUID, Voucher> storage = new HashMap<>();

        if (!append) {
            return storage;
        }

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            reader.readNext();  // Skip header
            while ((nextLine = reader.readNext()) != null) {
                UUID uuid = UUID.fromString(nextLine[0]);
                String code = nextLine[1];
                String value = nextLine[2];

                if ("fixed".equals(code)) {
                    Voucher voucher = new FixedAmountVoucher(uuid, Long.parseLong(value));
                    storage.put(uuid, voucher);
                }

                if ("percent".equals(code)) {
                    Voucher voucher = new PercentDiscountVoucher(uuid, Long.parseLong(value));
                    storage.put(uuid, voucher);
                }
            }
        } catch (Exception e) {
            logger.error("unexpected error occurred : ", e);
            throw new FileNotReadException("File was not read successfully.");
        }
        return storage;
    }

    @Override
    public void store(UUID voucherId, Voucher voucher, String filePath) {
        boolean append = Files.exists(Paths.get(filePath));
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, append))) {
            if (!append) {
                String[] header = {"UUID", "Voucher Type", "Voucher Value"};
                writer.writeNext(header);
            }

            if (voucher instanceof FixedAmountVoucher fixedAmountVoucher) {
                writer.writeNext(new String[]{voucherId.toString(), "fixed", Long.toString(fixedAmountVoucher.getAmount())});
                return;
            }

            if (voucher instanceof PercentDiscountVoucher percentDiscountVoucher) {
                writer.writeNext(new String[]{voucherId.toString(), "percent", Long.toString(percentDiscountVoucher.getPercent())});
            }
        } catch (Exception e) {
            logger.error("unexpected error occurred : ", e);
            throw new FileNotReadException("File was not read successfully.");
        }
    }
}
