package com.programmers.vouchermanagement.voucher.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.programmers.vouchermanagement.configuration.TestConfig;
import com.programmers.vouchermanagement.configuration.properties.AppProperties;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

@SpringJUnitConfig(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class FileVoucherRepositoryTest {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    AppProperties appProperties;

    @BeforeAll
    void setUp() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("저장된 json파일을 성공적으로 읽고 로드한다.")
    @Order(1)
    void testLoadingVoucherFileOnInit() {
        assertThat(appProperties.getVoucherFilePath(), is("src/test/resources/voucher-test.json"));
        assertThat(voucherRepository, notNullValue());
    }

    @Test
    @DisplayName("저장된 바우처가 없을 때 빈 리스트를 반환한다.")
    @Order(2)
    void testListVoucherSuccessful_ReturnEmptyList() {
        //when
        final List<Voucher> vouchers = voucherRepository.findAll();

        //then
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 저장에 성공한다.")
    @Order(3)
    void testVoucherCreationSuccessful() {
        //given
        final Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);

        //when
        final Voucher createdVoucher = voucherRepository.save(voucher);

        //then
        assertThat(createdVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("추가적으로 바우처를 저장하고 저장된 바우처들의 리스트를 반환한다.")
    @Order(4)
    void testListVoucherSuccessful_ReturnList() {
        //given
        final Voucher additionalVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(50), VoucherType.PERCENT);
        voucherRepository.save(additionalVoucher);

        //when
        final List<Voucher> vouchers = voucherRepository.findAll();

        //then
        assertThat(vouchers, hasSize(2));
    }
}
