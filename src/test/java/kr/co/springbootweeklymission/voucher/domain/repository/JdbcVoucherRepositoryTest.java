package kr.co.springbootweeklymission.voucher.domain.repository;

import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Test
    @Order(1)
    void 고정_할인_바우처를_등록() {
        //given
        Voucher voucher = Voucher.builder()
                .voucherId(UUID.randomUUID())
                .amount(10)
                .voucherPolicy(VoucherPolicy.FIXED_DISCOUNT)
                .build();

        //when
        Voucher actual = voucherRepository.save(voucher);

        //then
        assertThat(actual.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    @Order(2)
    void 등록된_모든_바우처를_조회() {
        //given
        Voucher voucher = Voucher.builder()
                .voucherId(UUID.randomUUID())
                .amount(10)
                .voucherPolicy(VoucherPolicy.PERCENT_DISCOUNT)
                .build();
        voucherRepository.save(voucher);

        //when
        List<Voucher> actual = voucherRepository.findAll();

        //then
        assertThat(actual).hasSize(2);
    }
}
