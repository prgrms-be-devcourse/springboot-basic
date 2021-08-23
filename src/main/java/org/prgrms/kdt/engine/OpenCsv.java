package org.prgrms.kdt.engine;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.SneakyThrows;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OpenCsv {

    public OpenCsv() {
    }

    public void saveFile(Map<UUID, Voucher> data, String filePath) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath))) {
            String[] header = "Voucher Type,UUID,Discount".split(",");
            csvWriter.writeNext(header);
            for (var entry : data.entrySet()) {
                csvWriter.writeNext(new String[]{
                        entry.getValue().getType().name(),              // voucher type
                        entry.getKey().toString(),                      // UUID
                        String.valueOf(entry.getValue().getDiscount())  // discount amount
                });
            }
        }
    }

    @SneakyThrows
    public Optional<Map<UUID, Voucher>> loadFile(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

            // pass header
            reader.readNext();

            String[] nextLine;
            Map<UUID, Voucher> data = new ConcurrentHashMap<>();

            while ((nextLine = reader.readNext()) != null) {
                UUID uuid = UUID.fromString(nextLine[1]);
                if (nextLine[0].equals(VoucherType.FixedAmountVoucher.name())) {
                    data.put(uuid, new FixedAmountVoucher(uuid, Long.parseLong(nextLine[2])));
                } else if (nextLine[0].equals(VoucherType.PercentDiscountVoucher.name())) {
                    data.put(uuid, new PercentDiscountVoucher(uuid, Long.parseLong(nextLine[2])));
                } else {
                    return Optional.empty();
                }
            }
            return Optional.of(data);
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

}
