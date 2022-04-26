package com.prgrms.management.voucher.repository;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {
    @Autowired
    VoucherRepository voucherRepository;

    Voucher voucher, voucherTwo;
    Voucher saveVoucher;
    Customer customer;
    UUID randomId;

    @BeforeAll
    void setup() {
        voucher = new VoucherRequest("fixed", "1000").create();
        voucherTwo = new VoucherRequest("percent", "90").create();
        randomId = UUID.randomUUID();
        saveVoucher = voucherRepository.save(voucher);
        voucherRepository.save(voucherTwo);
    }

    @AfterAll
    void cleanUp() {
        voucherRepository.deleteAll();
    }

    @Test
    @Order(2)
    void 성공_여러명의_Vouchers_검색() {
        //when
        List<Voucher> vouchers = voucherRepository.findAll();
        //then
        assertThat(vouchers.size()).isEqualTo(2);
    }

    @Nested
    @Order(1)
    class Voucher_정보_저장 {
        @Test
        void 성공_Voucher_저장() {
            //then
            assertThat(saveVoucher).isEqualTo(voucher);
        }
    }

    @Nested
    @Order(3)
    class Voucher_검색 {
        @Test
        void 성공_ID로_Voucher_검색() {
            //given;
            UUID voucherId = voucherTwo.getVoucherId();
            //when
            Optional<Voucher> voucherById = voucherRepository.findById(voucherId);
            //then
            assertThat(voucherById.get().getVoucherId()).isEqualTo(voucherId);
        }

        @Test
        void 실패_ID로_Voucher_검색() {
            //when
            Optional<Voucher> voucherById = voucherRepository.findById(randomId);
            //then
            assertThat(voucherById.isEmpty()).isEqualTo(true);
        }
    }

    @Nested
    @Order(4)
    class 삭제_Voucher {
        @Test
        void 성공_삭제_Voucher() {
            //given
            UUID voucherId = voucher.getVoucherId();
            //when
            voucherRepository.deleteById(voucherId);
            //then
            assertThat(voucherRepository.findById(voucherId).isEmpty()).isEqualTo(true);
        }

        @Test
        void 실패_삭제_Voucher() {
            //when
            voucherRepository.deleteById(randomId);
            //then
            assertThat(voucherRepository.findById(randomId).isEmpty()).isEqualTo(true);
        }
    }
}