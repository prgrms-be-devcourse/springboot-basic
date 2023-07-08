package com.prgmrs.voucher.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.prgmrs.voucher.enums.VoucherSelectionType;
import com.prgmrs.voucher.exception.FileNotReadException;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.Percent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Profile("!dev")
public class FileVoucherDatabase implements VoucherDatabase {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherDatabase.class);

    @Override
    public List<Voucher> load(String filePath) {
        boolean append = Files.exists(Paths.get(filePath));
        ArrayList<Voucher> storage = new ArrayList<>();

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
                if (VoucherSelectionType.FIXED_AMOUNT_VOUCHER.equals(VoucherSelectionType.of(code))) {
                    Amount amount = new Amount(Long.parseLong(value));
                    FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
                    Voucher voucher = new Voucher(uuid, fixedAmountDiscountStrategy);
                    storage.add(voucher);
                }

                if (VoucherSelectionType.PERCENT_DISCOUNT_VOUCHER.equals(VoucherSelectionType.of(code))) {
                    Percent percent = new Percent(Long.parseLong(value));
                    PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
                    Voucher voucher = new Voucher(uuid, percentDiscountStrategy);
                    storage.add(voucher);
                }
            }
        } catch (Exception e) {
            logger.error("unexpected error occurred : ", e);
            throw new FileNotReadException("File was not read successfully.");
        }
        return storage;
    }

    @Override
    public void store(Voucher voucher, String filePath) {
        boolean append = Files.exists(Paths.get(filePath));
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, append))) {
            if (!append) {
                String[] header = {"UUID", "Voucher Type", "Voucher Value"};
                writer.writeNext(header);
            }

            if (voucher.getDiscountStrategy() instanceof FixedAmountDiscountStrategy fixedAmountDiscountStrategy) {
                writer.writeNext(new String[]{voucher.getVoucherId().toString(), VoucherSelectionType.FIXED_AMOUNT_VOUCHER.getValue(), Long.toString(fixedAmountDiscountStrategy.getAmount().getValue())});
                return;
            }

            if (voucher.getDiscountStrategy() instanceof PercentDiscountStrategy percentDiscountStrategy) {
                writer.writeNext(new String[]{voucher.getVoucherId().toString(), VoucherSelectionType.PERCENT_DISCOUNT_VOUCHER.getValue(), Long.toString(percentDiscountStrategy.getPercent().getValue())});
            }
        } catch (Exception e) {
            logger.error("unexpected error occurred : ", e);
            throw new FileNotReadException("File was not read successfully.");
        }
    }
}
