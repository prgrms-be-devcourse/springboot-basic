package com.example.voucher_manager.domain.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryVoucherRepositoryTest {

    private static final VoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();
    private static final UUID fixedAmountVoucherId = UUID.fromString("860c7e9a-24d7-4700-ae37-ac162cc86fc4");
    private static final UUID percentDiscountVoucherId = UUID.fromString("c81d3233-6563-447e-896b-a1f8befc4923");

    @BeforeEach
    @DisplayName("모든 테스트 실행 전 Repository를 초기화 한다")
    public void clear() {
        memoryVoucherRepository.clear();
    }

    @Test
    @DisplayName("고정 할인 값을 갖는 바우처를 저장한다")
    public void createFixedVoucherTest() {
        Voucher fixedAmountVoucher = FixedAmountVoucher.of(UUID.randomUUID(), 3000L, VoucherType.FIXED);
        Optional<Voucher> result = memoryVoucherRepository.insert(fixedAmountVoucher);
        assertThat(fixedAmountVoucher).isEqualTo(result.get());
    }

    @Test
    @DisplayName("일정 할인율을 갖는 바우처를 저장한다")
    public void createPercentVoucherTest() {
        Voucher percentAmountVoucher = PercentDiscountVoucher.of(UUID.randomUUID(), 20L, VoucherType.PERCENT);
        Optional<Voucher> result = memoryVoucherRepository.insert(percentAmountVoucher);
        assertThat(percentAmountVoucher).isEqualTo(result.get());
    }

    @Test
    @DisplayName("Repository에 저장된 모든 데이터를 불러온다")
    public void findAll() {
        Voucher fixedAmountVoucher = FixedAmountVoucher.of(fixedAmountVoucherId, 1000L, VoucherType.FIXED);
        Voucher percentDiscountVoucher = PercentDiscountVoucher.of(percentDiscountVoucherId, 20L, VoucherType.PERCENT);
        memoryVoucherRepository.insert(fixedAmountVoucher);
        memoryVoucherRepository.insert(percentDiscountVoucher);

        List<Voucher> compare = Arrays.asList(
                fixedAmountVoucher,
                percentDiscountVoucher
        );
        assertThat(memoryVoucherRepository.findAll().equals(compare)).isTrue();
    }

}
