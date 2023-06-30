package kr.co.springbootweeklymission.voucher.domain.repository;

import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    void 고정_할인_바우처를_등록() {
        //given
        Voucher voucher = Voucher.builder().voucherId(UUID.randomUUID()).amount(10).voucherPolicy(VoucherPolicy.FIXED_DISCOUNT).build();

        //when
        Voucher actual = voucherRepository.save(voucher);

        //then
        assertThat(actual.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }
}
