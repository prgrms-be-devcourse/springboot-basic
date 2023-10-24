//package org.prgrms.kdtspringdemo.voucher.repository;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.prgrms.kdtspringdemo.voucher.domain.FixedDiscountPolicy;
//import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountPolicy;
//import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import java.util.Map;
//import java.util.UUID;
//
//import static org.hamcrest.MatcherAssert.*;
//import static org.hamcrest.Matchers.*;
//
//@SpringJUnitConfig
//@ActiveProfiles("local")
//class MemoryVoucherRepositoryTest {
//
//    @Configuration
//    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo"})
//    static class Config {
//    }
//    @Autowired
//    MemoryVoucherRepository memoryVoucherRepository;
//
//    @Test
//    @DisplayName("바우처 등록")
//    void insert() {
//        //given
//        VoucherPolicy fixedAmountVoucherPolicy = new FixedDiscountPolicy(UUID.randomUUID(), 100, "fixedAmount");
//        VoucherPolicy percentDiscountVoucherPolicy = new PercentDiscountPolicy(UUID.randomUUID(), 25, "percentDiscount");
//
//        //when
//        VoucherPolicy insertFixedAmountVoucherPolicy = memoryVoucherRepository.insert(fixedAmountVoucherPolicy);
//        VoucherPolicy insertPercentDiscountVoucherPolicy = memoryVoucherRepository.insert(percentDiscountVoucherPolicy);
//
//        //then
//        assertThat(fixedAmountVoucherPolicy, samePropertyValuesAs(insertFixedAmountVoucherPolicy));
//        assertThat(percentDiscountVoucherPolicy, samePropertyValuesAs(insertPercentDiscountVoucherPolicy));
//    }
//
//    @Test
//    @DisplayName("바우처 등록 실패")
//    void insertError() {
//        try {
//            //given
//            VoucherPolicy percentDiscountVoucherPolicy = new PercentDiscountPolicy(UUID.randomUUID(), 200, "percentDiscount");
//            //when
//            VoucherPolicy insertPercentDiscountVoucherPolicy = memoryVoucherRepository.insert(percentDiscountVoucherPolicy);
//        } catch (RuntimeException e) {
//            //then
//            assertThat(e, instanceOf(RuntimeException.class));
//        }
//    }
//
//    @Test
//    @DisplayName("voucherId로 바우처 검색")
//    void findById() {
//        //given
//        VoucherPolicy voucherPolicy = new PercentDiscountPolicy(UUID.randomUUID(), 20, "percentDiscount");
//        memoryVoucherRepository.insert(voucherPolicy);
//
//        //when
//        VoucherPolicy findVoucherPolicy = memoryVoucherRepository.findById(voucherPolicy.getVoucherId()).get();
//
//        //then
//        assertThat(voucherPolicy, samePropertyValuesAs(findVoucherPolicy));
//    }
//
//    @Test
//    @DisplayName("모든 바우처 조회")
//    void getAllVouchers() {
//        //given
//        memoryVoucherRepository.insert(new FixedDiscountPolicy(UUID.randomUUID(), 100, "fixedAmount"));
//        memoryVoucherRepository.insert(new FixedDiscountPolicy(UUID.randomUUID(), 2000, "fixedAmount"));
//        memoryVoucherRepository.insert(new PercentDiscountPolicy(UUID.randomUUID(), 5, "percentDiscount"));
//
//        //when
//        Map<UUID, VoucherPolicy> allVouchers = memoryVoucherRepository.findAll().get();
//
//        //then
//        assertThat(allVouchers.size(), is(3));
//    }
//}