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
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherServiceImplTest {
    private static final VoucherRepository repository = new MemoryVoucherRepository();
    private static final VoucherService voucherService = new VoucherServiceImpl(repository);


    @BeforeEach
    void beforeEach() {
        repository.clear();

        // given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30);

        repository.insert(fixedAmountVoucher);
        repository.insert(percentDiscountVoucher);
    }

    @Test
    @DisplayName("Voucher Service를 통해 uuid를 통해 voucher들을 모두 정상적으로 탐색하는지 검증하는 테스트")
    void getVoucher() {
        // given
        Map<UUID, Voucher> vouchers = voucherService.findAllVouchers();

        // when
        List<UUID> uuids = vouchers.values().stream().map(Voucher::getVoucherId).toList();

        // then
        assertThat(uuids.stream().map(voucherService::getVoucher).toList())
                .hasSize(2);
    }

    @Test
    @DisplayName("Voucher Service를 통해 미리 저장해 놓은 바우처들을 Map으로부터 정상적으로 모두 탐색하는지 검증하는 테스트")
    void findAllVouchers() {
        // when
        Map<UUID, Voucher> vouchers = voucherService.findAllVouchers();

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