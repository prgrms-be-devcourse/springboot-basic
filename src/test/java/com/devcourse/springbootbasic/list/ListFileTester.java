package com.devcourse.springbootbasic.list;

import com.devcourse.springbootbasic.engine.voucher.service.VoucherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
@ActiveProfiles(profiles = {"default"})
public class ListFileTester {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private VoucherService voucherService;

    @Test
    void voucherFileTest() throws IOException {
        String filepath = applicationContext.getResource("file:src/main/resources/voucher_record.txt")
                .getFile()
                .getPath();
        long expectedValue = Files.lines(Path.of(filepath)).count();

        long resultValue = voucherService.getAllVouchers().size();

        Assertions.assertEquals(expectedValue, resultValue);
    }

}
