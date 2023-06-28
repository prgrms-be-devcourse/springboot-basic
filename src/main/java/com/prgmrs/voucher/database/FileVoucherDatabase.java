package com.prgmrs.voucher.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.PercentDiscountVoucher;
import com.prgmrs.voucher.model.Voucher;
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
    String filename = "src/main/csv/vouchers.csv";

    @Override
    public Map<UUID, Voucher> load() {
        boolean append = Files.exists(Paths.get(filename));
        Map<UUID, Voucher> cache = new HashMap<>();

        if (!append) {
            return cache;
        }

        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            String[] nextLine;
            Voucher voucher;
            reader.readNext();  // Skip header
            while ((nextLine = reader.readNext()) != null) {
                UUID uuid = UUID.fromString(nextLine[0]);
                String code = nextLine[1];
                String value = nextLine[2];

                if(code.equals("fixed")) {
                    voucher = new FixedAmountVoucher(uuid, Long.parseLong(value));
                    cache.put(uuid, voucher);
                } else if (code.equals("percent")) {
                    voucher = new PercentDiscountVoucher(uuid, Long.parseLong(value));
                    cache.put(uuid, voucher);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }

    @Override
    public void store(UUID voucherId, Voucher voucher) {
        boolean append = Files.exists(Paths.get(filename));
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename, append))) {
            if(!append) {
                String[] header = { "UUID", "Voucher Type", "Voucher Value" };
                writer.writeNext(header);
            }

            if(voucher instanceof FixedAmountVoucher fixedAmountVoucher) {
                 writer.writeNext(new String[]{ voucherId.toString(), "fixed", Long.toString(fixedAmountVoucher.getAmount())});
            } else if(voucher instanceof PercentDiscountVoucher percentDiscountVoucher) {
                 writer.writeNext(new String[]{ voucherId.toString(), "percent", Long.toString(percentDiscountVoucher.getPercent())});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
