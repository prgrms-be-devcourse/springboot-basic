package org.prgrms.kdt.voucher.repository;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemoryVoucherRepositoryTest {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.voucher.repository"}
    )
    static class Config{
    }

    @Autowired
    VoucherRepository voucherRepository;

    Voucher newVoucher;

    @BeforeAll
    void setUp(){
       newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
    }

    @AfterEach
    void afterEach(){
        voucherRepository.clearAllVouchers();
    }

    @Test
    @DisplayName("Voucher를 등록한다.")
    void testInsert(){
        //given //when
        Voucher insertResult = voucherRepository.insert(newVoucher);

        //then
        assertThat(insertResult, samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("id에 해당하는 Voucher 객체를 반환한다.")
    void testFindById(){
        //given
        voucherRepository.insert(newVoucher);

        //when
        Optional<Voucher> findResult = voucherRepository.findById(newVoucher.getVoucherId());

        //then
        assertThat(findResult.isEmpty(), is(false));
        assertThat(findResult.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("존재하지 않는 id에 대한 Voucher를 찾는다면 Empty 값을 반환한다.")
    void testFindByIdNotExist(){
        //given //when
        Optional<Voucher> findResult = voucherRepository.findById(newVoucher.getVoucherId());

        //then
        assertThat(findResult.isEmpty(), is(true));
    }

    @Test
    @DisplayName("등록된 모든 Voucher를 반환한다.")
    void testFindAllVouchers(){
        //given
        voucherRepository.insert(newVoucher);

        PercentDiscountVoucher newPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        voucherRepository.insert(newPercentVoucher);

        //when
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();

        //then
        assertThat(allVouchers.size(), is(2));
        assertThat(allVouchers, containsInAnyOrder(newVoucher, newPercentVoucher));

    }

    @Test
    @DisplayName("모든 Voucher를 삭제한다.")
    void testClearAllVouchers(){
        //given
        voucherRepository.insert(newVoucher);

        //when
        voucherRepository.clearAllVouchers();

        //then
        assertThat(voucherRepository.findAllVouchers().size(), is(0));
    }
}