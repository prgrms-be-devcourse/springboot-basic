package org.programmers.weekly.mission.domain.voucher.repository;

import org.junit.jupiter.api.*;
import org.programmers.weekly.mission.domain.voucher.model.FixedAmountVoucher;
import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemoryVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.weekly.mission.domain.voucher"})
    static class Config {}

    @Autowired
    MemoryVoucherRepository memoryVoucherRepository;

    FixedAmountVoucher fixedAmountVoucher;

    @BeforeAll
    void setUp() {
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
    }

    @Test
    @Order(1)
    @DisplayName("바우처가 저장되어야 한다")
    void testInsert() {
        assertThat(memoryVoucherRepository.insert(fixedAmountVoucher), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("바우처 리스트를 반환해야 한다")
    void testGetVoucherList() {
        List<Voucher> all = memoryVoucherRepository.getVoucherList();
        assertThat(all, everyItem(samePropertyValuesAs(fixedAmountVoucher)));
    }
}