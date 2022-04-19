package com.mountain.voucherApp.repository;

import com.mountain.voucherApp.properties.FileRepositoryProperties;
import com.mountain.voucherApp.voucher.VoucherEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

class FileVoucherRepositoryTest {

    public static final Logger log = LoggerFactory.getLogger(FileVoucherRepositoryTest.class);
    private static FileRepositoryProperties properties = new FileRepositoryProperties("vouchers", "list.txt");
    private static FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(properties);

    @BeforeEach
    void init() throws Exception {
        fileVoucherRepository.postConstruct();
    }

    @DisplayName("파일에 voucher 정보를 저장한다.")
    @Test
    public void insertTest() {
        // when
        for (int i = 0; i < 10; i++) {
            // given
            VoucherEntity voucherEntity = new VoucherEntity(UUID.randomUUID(), 1, 100L);
            // when, then
            Assertions.assertDoesNotThrow(() -> fileVoucherRepository.insert(voucherEntity));
        }
    }

    @DisplayName("파일에서 바우처 정보를 읽어 온다.")
    @Test
    public void findAllTest() throws Exception {
        // when, then
        Assertions.assertDoesNotThrow(() -> fileVoucherRepository.findAll());
    }

}