package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.FixedAmountVoucher;
import com.programmers.springweekly.domain.voucher.PercentDiscountVoucher;
import com.programmers.springweekly.domain.voucher.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryVoucherRepositoryTest {

    VoucherRepository voucherRepository;

    @BeforeEach
    void createVoucherRepository() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    @DisplayName("메모리 저장소에 고정 할인 바우처가 정상적으로 등록된다.")
    void saveFixedVoucherToMemoryRepository() {
        // given
        Voucher voucher = new FixedAmountVoucher(1000);

        // when
        voucherRepository.save(voucher);

        // then
        assertThat(voucherRepository.getList().get(voucher.getVoucherId())).isEqualTo(voucher);
    }

    @Test
    @DisplayName("메모리 저장소에 퍼센트 할인 바우처가 정상적으로 등록된다.")
    void savePercentVoucherToMemoryRepository() {
        // given
        Voucher voucher = new PercentDiscountVoucher(100);

        // when
        voucherRepository.save(voucher);

        // then
        assertThat(voucherRepository.getList().get(voucher.getVoucherId())).isEqualTo(voucher);
    }

    @Test
    @DisplayName("메모리 저장소에 여러 바우처를 등록했을 때 정상적으로 바우처 리스트를 가져온다.")
    void getVoucherList() {
        // given
        Voucher voucher1 = new FixedAmountVoucher(1000);
        Voucher voucher2 = new FixedAmountVoucher(500);
        Voucher voucher3 = new PercentDiscountVoucher(50);
        Voucher voucher4 = new PercentDiscountVoucher(30);

        Map<UUID, Voucher> vouchers = Map.of(
                voucher1.getVoucherId(), voucher1,
                voucher2.getVoucherId(), voucher2,
                voucher3.getVoucherId(), voucher3,
                voucher4.getVoucherId(), voucher4
        );

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.save(voucher4);


        // then
        assertThat(voucherRepository.getList()).isEqualTo(vouchers);
    }
}
