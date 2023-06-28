package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.application.constant.YamlProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

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