package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.application.constant.YamlProperties;
import com.devcourse.springbootbasic.application.domain.FixedAmountVoucher;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.yaml.snakeyaml.Yaml;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileVoucherRepositoryTest {

    FileVoucherRepository fileVoucherRepository;
    YamlProperties yamlProperties;

    @BeforeEach
    void init() {
        yamlProperties = new YamlProperties();
        fileVoucherRepository = new FileVoucherRepository(yamlProperties);
    }

    @Test
    void test() {
        assertNull(yamlProperties.getVoucherRecordPath());
    }

}