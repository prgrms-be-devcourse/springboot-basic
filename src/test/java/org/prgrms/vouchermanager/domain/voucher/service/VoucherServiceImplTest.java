package org.prgrms.vouchermanager.domain.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.domain.VoucherRepository;
import org.prgrms.vouchermanager.domain.voucher.persistence.MemoryVoucherRepository;

import java.util.UUID;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VoucherServiceImplTest {

    @Test
    @DisplayName("createVoucher는 VoucherRepository에 insert한다.")
    void createVoucherTest() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        VoucherService voucherService = new VoucherServiceImpl(voucherRepository);

        //when
        UUID insertedVoucherId = voucherService.createVoucher("FIXED", 10L);

        //then
        assertThat(voucherRepository.findById(insertedVoucherId)).isNotEmpty();
    }

    @Test
    @DisplayName("존재하는 VoucherList를 출력한다.")
    void allVoucherToString() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        VoucherService voucherService = new VoucherServiceImpl(voucherRepository);
        LongStream.range(1, 10).forEach(i -> {
            voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),i));
            voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(),i));
        });

        //when
        StringBuilder sb = new StringBuilder();
        voucherRepository.getAll().forEach(v -> sb.append(v).append("\n"));

        //then
        assertEquals(sb.toString(), voucherService.allVouchersToString());
    }

    @Test
    @DisplayName("createVoucher에서 잘못된 타입을 입력 받았을 때, 예외를 던진다.")
    void testWithIllegalTypeArg() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        VoucherService voucherService = new VoucherServiceImpl(voucherRepository);

        //then
        assertThatThrownBy(() -> voucherService.createVoucher("WringVoucherType", 10L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("저장되지 않은 voucherId로 findVoucher할 경우 예외를 던진다.")
    void findVoucher_test() {
        //given
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        VoucherService voucherService = new VoucherServiceImpl(voucherRepository);

        //then
        assertThatThrownBy(() -> voucherService.findVoucher(UUID.randomUUID())).isInstanceOf(IllegalArgumentException.class);
    }

}