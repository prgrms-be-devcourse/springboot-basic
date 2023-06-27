package com.devcourse.springbootbasic.list;

import com.devcourse.springbootbasic.application.constant.YamlProperties;
import com.devcourse.springbootbasic.application.service.VoucherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
@ActiveProfiles(profiles = {"default"})
public class ListFileTester {

    @Autowired
    private YamlProperties yamlProperties;

    @Autowired
    private VoucherService voucherService;

    @Test
    void voucherFileTest() throws IOException {
        String filepath = yamlProperties.getVoucherRecordPath();
        long expectedValue = Files.lines(Path.of(filepath)).count();

        long resultValue = voucherService.getAllVouchers().size();

        Assertions.assertEquals(expectedValue, resultValue);
    }

}
