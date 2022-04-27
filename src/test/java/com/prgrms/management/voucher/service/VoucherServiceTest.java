package com.prgrms.management.voucher.service;

import com.prgrms.management.config.exception.NotExistException;
import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerRequest;
import com.prgrms.management.customer.repository.CustomerRepository;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.dto.VoucherRequest;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.repository.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class VoucherServiceTest {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    VoucherService voucherService;

    VoucherRequest voucherRequest = new VoucherRequest("fixed", "1000");
    Voucher voucher;
    Customer customer;

    @BeforeEach
    void setup() {
        voucher = voucherService.createVoucher(voucherRequest);
    }

    @AfterEach
    void cleanUp() {
        voucherRepository.deleteAll();
    }

    @Test
    void Voucher_저장() {
        //then
        Assertions.assertThat(voucher.getVoucherType()).isEqualTo(voucherRequest.getVoucherType());
    }

    @Nested
    class Vouchers_조회 {
        @Test
        void 모든_Vouchers_조회() {
            //when
            List<Voucher> voucherList = voucherService.findAll();
            //then
            Assertions.assertThat(voucherList.size()).isEqualTo(1);
        }

        @Test
        void 조건별_Vouchers_조회() {
            //when
            List<Voucher> vouchers = voucherService.findAllByVoucherTypeOrCreatedAt(VoucherType.FIXED, LocalDate.now());
            //then
            Assertions.assertThat(vouchers.size()).isEqualTo(1);
        }
    }

    @Nested
    class Id로_Voucher_조회 {
        @Test
        void Voucher_조회() {
            //when
            Voucher voucherById = voucherService.findById(voucher.getVoucherId());
            //then
            Assertions.assertThat(voucherById).isNotNull();
        }

        @Test
        void 존재하지_않는_ID_인한_Voucher_조회_실패() {
            //then
            Assertions.assertThatThrownBy(() -> voucherService.findById(UUID.randomUUID()))
                    .isInstanceOf(NotExistException.class);
        }
    }

    @Nested
    class Voucher_업데이트 {
        @Test
        void Voucher_업데이트() {
            customer = new Customer(new CustomerRequest("customerA", "prgrms@naver.com", "normal"));
            customerRepository.save(customer);
            //when
            voucherService.updateByCustomerId(voucher.getVoucherId(), customer.getCustomerId());
            //then
            Assertions.assertThat(voucher).isNotNull();
        }

        @Test
        void 존재하지_않는_ID_인한_Voucher_업데이트_실패() {
            Assertions.assertThatThrownBy(() -> voucherService.updateByCustomerId(voucher.getVoucherId(), UUID.randomUUID()))
                    .isInstanceOf(NotExistException.class);
        }
    }
}