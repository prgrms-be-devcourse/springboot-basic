package com.prgrms.management.voucher.repository;

import com.prgrms.management.config.exception.NotFoundException;
import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerRequest;
import com.prgrms.management.customer.repository.CustomerRepository;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CustomerRepository customerRepository;

    Voucher voucher = new VoucherRequest("fixed", "1000").create();
    Voucher voucherTwo = new VoucherRequest("percent", "90").create();
    Voucher save;
    Customer customer;
    UUID randomId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        save = voucherRepository.save(voucher);
        voucherRepository.save(voucherTwo);
        customer = customerRepository.save(new Customer(new CustomerRequest("customerA", "prgrms@naver.com", "normal")));
    }

    @AfterEach
    void cleanUp() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void 여러명의_Vouchers_검색() {
        //when
        List<Voucher> vouchers = voucherRepository.findAll();
        //then
        assertThat(vouchers.size()).isEqualTo(2);
    }

    @Test
    void Voucher_저장() {
        //then
        assertThat(save).isEqualTo(voucher);
    }

    @Nested
    class Voucher_검색 {
        @Test
        void ID로_Voucher_검색() {
            //given;
            UUID voucherId = voucherTwo.getVoucherId();
            //when
            Optional<Voucher> voucherById = voucherRepository.findById(voucherId);
            //then
            assertThat(voucherById.get().getVoucherId()).isEqualTo(voucherId);
        }

        @Test
        void 존재하지_않는_ID로_인한_Voucher_검색_실패() {
            //when
            Optional<Voucher> voucherById = voucherRepository.findById(randomId);
            //then
            assertThat(voucherById.isEmpty()).isEqualTo(true);
        }
    }

    @Nested
    class Voucher_업데이트 {
        @Test
        void Voucher_업데이트() {
            voucherRepository.updateByCustomerId(voucher.getVoucherId(), customer.getCustomerId());
        }

        @Test
        void 존재하지_않는_CustomerId로_인한_Voucher_업데이트_실패() {
            Assertions.assertThatThrownBy(() -> voucherRepository.updateByCustomerId(voucher.getVoucherId(), UUID.randomUUID()))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @Test
    void Voucher_삭제() {
        //given
        UUID voucherId = voucher.getVoucherId();
        //when
        voucherRepository.deleteById(voucherId);
        //then
        assertThat(voucherRepository.findById(voucherId).isEmpty()).isEqualTo(true);
    }
}