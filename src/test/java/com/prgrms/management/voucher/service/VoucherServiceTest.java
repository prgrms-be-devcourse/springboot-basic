package com.prgrms.management.voucher.service;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerRequest;
import com.prgrms.management.customer.repository.CustomerRepository;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.repository.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    VoucherService voucherService;

    VoucherRequest voucherRequest, voucherRequestTwo;
    Voucher voucher;
    UUID randomId;

    @BeforeAll
    void setup() {
        voucherRequest = new VoucherRequest("fixed", "1000");
        voucherRequestTwo = new VoucherRequest("percent", "90");
        randomId = UUID.randomUUID();
        voucher = voucherService.createVoucher(voucherRequest);
    }

    @AfterAll
    void cleanUp() {
        voucherRepository.deleteAll();
    }

    @Test
    @Order(1)
    void 저장_Voucher() {
        //then
        Assertions.assertThat(voucher.getVoucherType()).isEqualTo(voucherRequest.getVoucherType());
    }

    @Nested
    @Order(2)
    class 모든_Vouchers_조회 {
        @Test
        void 모든_Vouchers_조회() {
            //when
            List<Voucher> voucherList = voucherService.findAll();
            //then
            Assertions.assertThat(voucherList.size()).isEqualTo(1);
        }

        @Test
        void 모든_FIXED_Vouchers_조회() {
            //when
            List<UUID> fixedList = voucherService.findCustomersByVoucherType(VoucherType.FIXED);
            //then
            Assertions.assertThat(fixedList.size()).isEqualTo(1);
        }

        @Test
        void 모든_PERCENT_Vouchers_조회() {
            //when
            List<UUID> percentList = voucherService.findCustomersByVoucherType(VoucherType.PERCENT);
            //then
            Assertions.assertThat(percentList.size()).isEqualTo(0);
        }
    }
}