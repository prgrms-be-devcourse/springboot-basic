package com.prgrms.springbasic.util;

import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.domain.voucher.entity.DiscountType;
import com.prgrms.springbasic.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Component
public class CsvFileUtil {
    private static final Logger log = LoggerFactory.getLogger(CsvFileUtil.class);

    public static void addVoucherToFile(String filePath, Voucher voucher) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String csvLine = String.format("%s,%s,%s",
                    voucher.getVoucherId(), voucher.getDiscountType(), voucher.getDiscountValue());
            writer.write(csvLine);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<UUID, Voucher> readVoucherFromFile(String filePath) {
        Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                UUID voucherId = UUID.fromString(parts[0]);
                String discountType = parts[1];
                long discountValue = Long.parseLong(parts[2]);
                Voucher voucher = switch (DiscountType.find(discountType)) {
                    case FIXED -> new FixedAmountVoucher(voucherId, discountType, discountValue);
                    case PERCENT -> new PercentDiscountVoucher(voucherId, discountType, discountValue);
                };
                vouchers.put(voucherId, voucher);
            });
        } catch (IOException e) {
            log.error("The file does not exist. fileName : {}", filePath);
        }
        return vouchers;
    }

    public static Map<UUID, Customer> readCustomerFromFile(String filePath) {
        Map<UUID, Customer> customers = new ConcurrentHashMap<>();
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                UUID customerId = UUID.fromString(parts[0]);
                String customerName = parts[1];
                customers.put(customerId, new Customer(customerId, customerName));
            });
        } catch (IOException e) {
            log.error("The file does not exist. fileName : {}", filePath);
        }
        return customers;
    }
}
