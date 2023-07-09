package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class VoucherFileWriter {

    public void write(Voucher voucher, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(voucher.getName());
            writer.write(",");
            writer.write(voucher.getVoucherId().toString());
            writer.write(",");
            writer.write(Long.toString(voucher.getValue()));
            writer.newLine();
        }
    }
}
