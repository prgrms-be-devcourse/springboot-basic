package com.demo.voucher.service;

import com.demo.voucher.domain.FixedAmountVoucher;
import com.demo.voucher.domain.PercentDiscountVoucher;
import com.demo.voucher.domain.Voucher;
import com.demo.voucher.domain.VoucherType;
import com.demo.voucher.repository.MemoryVoucherRepository;
import com.demo.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherServiceImplTest {
    private static final VoucherRepository repository = new MemoryVoucherRepository();
    private static final VoucherService voucherService = new VoucherServiceImpl(repository);


    @BeforeEach
    void beforeEach() {
        repository.clear();

        // given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1000);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(30);

        repository.insert(fixedAmountVoucher);
        repository.insert(percentDiscountVoucher);
    }

    @Test
    @DisplayName("Voucher Service에서 uuid를 통해 생성한 바우처를 정상적으로 가져오는지 확인하는 테스트")
    void getVoucher() {
        // given
        repository.insert(new FixedAmountVoucher(2000));

        // when
        UUID uuid = repository.findAll().get(0).getVoucherId();
        Voucher foundVoucher = voucherService.getVoucher(uuid);

        // then
        assertEquals(foundVoucher.getVoucherId(), uuid);
    }


    @Test
    @DisplayName("Voucher service에서 uuid로 바우처를 탐색하지 못하는 경우 던지는 예외 처리 검증 테스트")
    void getVoucher_UUID로_바우처를_찾지못하는_예외처리_확인_테스트() {
        // given
        UUID wrongUUID = UUID.randomUUID();

        Exception exception = assertThrows(RuntimeException.class, () -> voucherService.getVoucher(wrongUUID));

        // then
        String expectedMessage = "바우처를 찾을 수 없습니다.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Voucher Service를 통해 미리 저장해 놓은 바우처들을 Map으로부터 정상적으로 모두 탐색하는지 검증하는 테스트")
    void findAllVouchers() {
        // when
        List<Voucher> vouchers = voucherService.findAllVouchers();

        // then
        assertThat(vouchers).hasSize(2);
    }

    @Test
    @DisplayName("Voucher Service를 통해 새로운 Voucher 추가 했을 때 Map의 크기가 하나 커지는지 검증하는 테스트")
    void createVoucher() {
        // given
        VoucherType fixedVoucherType = VoucherType.FIXED_AMOUNT;
        String amount = "3000";

        // when
        voucherService.createVoucher(fixedVoucherType, amount);

        // then
        assertThat(voucherService.findAllVouchers())
                .hasSize(3);
    }
}