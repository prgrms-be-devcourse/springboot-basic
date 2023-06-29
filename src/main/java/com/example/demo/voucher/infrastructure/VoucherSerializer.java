package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.domain.FixedAmountVoucher;
import com.example.demo.voucher.domain.PercentDiscountVoucher;
import com.example.demo.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Profile("dev")
public class VoucherSerializer {
    @Value("${file.path}") private String filePath;

    public void serialize(List<Voucher> vouchers) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Voucher voucher : vouchers) {
                writer.write(voucher.getName());
                writer.write(",");
                writer.write(voucher.getVoucherId().toString());
                writer.write(",");
                writer.write(Long.toString(voucher.getValue()));
                writer.newLine();
            }
        }
    }

    public List<Voucher> deserialize() throws IOException {
        List<Voucher> vouchers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                UUID id = UUID.fromString(parts[1]);
                long value = Long.parseLong(parts[2]);

                if ("FixedAmountVoucher".equals(name)) {
                    vouchers.add(new FixedAmountVoucher(id, value));
                } else if ("PercentDiscountVoucher".equals(name)) {
                    vouchers.add(new PercentDiscountVoucher(id, value));
                }
            }
        }
        return vouchers;
    }
}
