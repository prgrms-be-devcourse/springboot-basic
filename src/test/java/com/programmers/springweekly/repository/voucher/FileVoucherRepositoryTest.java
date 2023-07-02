package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.FixedAmountVoucher;
import com.programmers.springweekly.domain.voucher.PercentDiscountVoucher;
import com.programmers.springweekly.domain.voucher.Voucher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Slf4j
public class FileVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("파일 저장소에 고정 할인 바우처가 정상적으로 등록된다.")
    void saveFixedVoucherToFileRepository() {
        // given
        Voucher voucher = new FixedAmountVoucher(1000);

        // when
        voucherRepository.save(voucher);

        // then
        assertThat(voucherRepository.getList().get(voucher.getVoucherId()))
                .usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("파일 저장소에 퍼센트 할인 바우처가 정상적으로 등록된다.")
    void savePercentVoucherToMemoryRepository() {
        // given
        Voucher voucher = new PercentDiscountVoucher(100);

        // when
        voucherRepository.save(voucher);

        // then
        assertThat(voucherRepository.getList().get(voucher.getVoucherId()))
                .usingRecursiveComparison()
                .isEqualTo(voucher);
    }
}
