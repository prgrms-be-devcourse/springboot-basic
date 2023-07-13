package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.application.VoucherType;
import com.example.demo.voucher.domain.Voucher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoucherFileWriterTest {

    @Test
    void write(@TempDir Path tempDir) throws IOException {
        // Given
        String filePath = tempDir.resolve("test.csv").toString();
        VoucherFileWriter writer = new VoucherFileWriter();
        long value = 10;
        Voucher voucher = VoucherType.PERCENT_DISCOUNT_VOUCHER.createVoucher(UUID.randomUUID(), value);

        // When
        writer.write(voucher, filePath);

        // Then
        assertTrue(Files.exists(Path.of(filePath)));
    }
}
