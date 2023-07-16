package com.programmers.springweekly.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springweekly.domain.voucher.FixedAmountVoucher;
import com.programmers.springweekly.domain.voucher.PercentDiscountVoucher;
import com.programmers.springweekly.domain.voucher.Voucher;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = {FileVoucherRepository.class})
public class FileVoucherRepositoryTest {

    @Autowired
    private FileVoucherRepository voucherRepository;

    @Test
    @DisplayName("바우처 파일에 고정 할인 바우처를 저장할 수 있다.")
    void saveFixedVoucherToFileRepository() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        // when
        voucherRepository.save(voucher);

        // then
        assertThat(voucherRepository.findAll().get(voucher.getVoucherId()))
                .usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처 파일에 퍼센트 할인 바우처를 저장할 수 있다.")
    void savePercentVoucherToFileRepository() {
        // given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 100);

        // when
        voucherRepository.save(voucher);

        // then
        assertThat(voucherRepository.findAll().get(voucher.getVoucherId()))
                .usingRecursiveComparison()
                .isEqualTo(voucher);
    }

}
