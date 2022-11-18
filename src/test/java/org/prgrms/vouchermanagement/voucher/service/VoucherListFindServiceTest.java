package org.prgrms.vouchermanagement.voucher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.*;

class VoucherListFindServiceTest {

    private final VoucherRepository voucherRepository = mock(VoucherRepository.class);
    private final VoucherListFindService voucherListFindService = new VoucherListFindService(voucherRepository);

    @BeforeEach
    void init() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("바우처 리스트 정상 출력 확인")
    void voucherList() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50);

        voucherRepository.save(fixedAmountVoucher);
        voucherRepository.save(percentDiscountVoucher);

        when(voucherRepository.findAll())
                .thenReturn(List.of(fixedAmountVoucher, percentDiscountVoucher));

        // when
        List<Voucher> allVouchers = voucherListFindService.findAllVouchers();

        // then
        verify(voucherRepository).findAll();
        assertThat(allVouchers).hasSize(2);
        assertThat(allVouchers)
                .extracting("voucherId", "discountAmount", "voucherType")
                .contains(tuple(fixedAmountVoucher.getVoucherId(), fixedAmountVoucher.getDiscountAmount(), fixedAmountVoucher.getVoucherType()),
                        tuple(percentDiscountVoucher.getVoucherId(), percentDiscountVoucher.getDiscountAmount(), percentDiscountVoucher.getVoucherType()));
    }
}