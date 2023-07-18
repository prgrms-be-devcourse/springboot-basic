package com.example.demo.domain.voucher;

import java.util.List;
import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryVoucherRepositoryTest {

    private VoucherRepository voucherRepository;

    @BeforeEach
    void createVoucherRepository() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    @DisplayName("메모리 저장소에 고정 할인 바우처를 저장할 수 있다.")
    void saveFixedVoucherToMemoryRepository() {
        // given
        Voucher voucher = new FixedAmountVoucher(1000);

        // when
        voucherRepository.save(voucher);

        // then
        Assertions.assertThat(voucherRepository.findById(voucher.getId()).orElseThrow(NoSuchElementException::new))
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("메모리 저장소에 퍼센트 할인 바우처를 저장할 수 있다.")
    void savePercentVoucherToMemoryRepository() {
        // given
        Voucher voucher = new PercentDiscountVoucher(100);

        // when
        voucherRepository.save(voucher);

        // then
        Assertions.assertThat(voucherRepository.findById(voucher.getId()).orElseThrow(NoSuchElementException::new))
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("메모리 저장소에 여러 바우처를 등록한 뒤, 바우처 리스트를 가져올 수 있다.")
    void getVoucherList() {
        // given
        Voucher voucher1 = new FixedAmountVoucher(1000);
        Voucher voucher2 = new FixedAmountVoucher(500);
        Voucher voucher3 = new PercentDiscountVoucher(50);
        Voucher voucher4 = new PercentDiscountVoucher(30);

        List<Voucher> vouchers = List.of(voucher1, voucher2, voucher3, voucher4);

        // when
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.save(voucher4);

        // then
        Assertions.assertThat(voucherRepository.findAll()).isEqualTo(vouchers);
    }
}