package com.mountain.voucherapp.dao.voucher;

import com.mountain.voucherapp.config.properties.FileRepositoryProperties;
import com.mountain.voucherapp.model.VoucherEntity;
import com.mountain.voucherapp.model.enums.DiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

class FileVoucherRepositoryTest {

    public static final Logger log = LoggerFactory.getLogger(FileVoucherRepositoryTest.class);
    private static FileRepositoryProperties properties = new FileRepositoryProperties("vouchers", "list.txt");
    private static FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(properties);

    @BeforeEach
    void init() throws Exception {
        fileVoucherRepository.remove();
        fileVoucherRepository.postConstruct();
    }

    @DisplayName("파일에 voucher 정보를 저장한다.")
    @Test
    public void insertTest() {
        // when
        for (int i = 0; i < 10; i++) {
            // given
            VoucherEntity voucherEntity = new VoucherEntity(UUID.randomUUID(), DiscountPolicy.FIXED, 100L);
            // when, then
            Assertions.assertDoesNotThrow(() -> fileVoucherRepository.insert(voucherEntity));
        }
    }

    @DisplayName("파일에서 voucher 정보를 읽어 온다.")
    @Test
    public void findAllTest() throws Exception {

        for (int i = 0; i < 3; i++) {
            // given
            VoucherEntity voucherEntity = new VoucherEntity(UUID.randomUUID(), DiscountPolicy.FIXED, 100L);
            // when, then
            Assertions.assertDoesNotThrow(() -> fileVoucherRepository.insert(voucherEntity));
        }
        // when, then
        Assertions.assertDoesNotThrow(() -> fileVoucherRepository.findAll());
        Assertions.assertEquals(3, fileVoucherRepository.findAll().size());
    }

    @DisplayName("할인 정책과 금액을 조건으로 데이터를 검색할 수 있다.")
    @Test
    public void findDiscountPolicyAndAmountTest() throws Exception {
        // given
        VoucherEntity voucherEntity = new VoucherEntity(UUID.randomUUID(), DiscountPolicy.PERCENT, 3L);
        // when
        fileVoucherRepository.insert(voucherEntity);
        Optional<VoucherEntity> existVoucher = fileVoucherRepository.findByDiscountPolicyAndAmount(voucherEntity.getDiscountPolicy(), voucherEntity.getDiscountAmount());
        Optional<VoucherEntity> notExistVoucher = fileVoucherRepository.findByDiscountPolicyAndAmount(voucherEntity.getDiscountPolicy(), voucherEntity.getDiscountAmount() * 2);
        // then
        assertThat(existVoucher.isEmpty(), is(false));
        assertThat(notExistVoucher.isEmpty(), is(true));
        assertThat(existVoucher.get(), samePropertyValuesAs(voucherEntity));
    }

}