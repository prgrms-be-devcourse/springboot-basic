package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.domain.FixedAmountVoucher;
import com.example.demo.voucher.domain.PercentDiscountVoucher;
import com.example.demo.voucher.domain.Voucher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoucherSerializerTest {

    VoucherSerializer voucherSerializer;
    List<Voucher> vouchers;
    Path filePath;

    @BeforeEach
    public void setUp() {
        voucherSerializer = new VoucherSerializer();
        filePath = Paths.get("testvoucher.csv");
        ReflectionTestUtils.setField(voucherSerializer, "filePath", filePath.toString());

        // sample data
        vouchers = new ArrayList<>();
        vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), 100L));
        vouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), 20L));
    }

    @Test
    @DisplayName("파일에 읽고 쓰기 테스트")
    public void testSerializationAndDeserialization() throws IOException {
        voucherSerializer.serialize(vouchers);
        List<Voucher> deserializedVouchers = voucherSerializer.deserialize();

        assertEquals(vouchers.size(), deserializedVouchers.size());
        for (int i = 0; i < vouchers.size(); i++) {
            assertEquals(vouchers.get(i).getName(), deserializedVouchers.get(i).getName());
            assertEquals(vouchers.get(i).getVoucherId(), deserializedVouchers.get(i).getVoucherId());
            assertEquals(vouchers.get(i).getValue(), deserializedVouchers.get(i).getValue());
        }
    }

    @AfterEach
    public void cleanUp() throws IOException {
        Files.deleteIfExists(filePath);
    }
}
