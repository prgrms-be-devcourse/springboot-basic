package org.prgrms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.memory.VoucherDBMemory;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;
import org.prgrms.voucher.voucherType.FixedAmountVoucher;
import org.prgrms.voucher.voucherType.PercentDiscountVoucher;
import org.prgrms.voucher.voucherType.Voucher;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @InjectMocks
    private VoucherService voucherService;

    @Mock
    private VoucherDBMemory voucherDBMemory;

    @DisplayName("id로 바우처를 찾아 리턴한다")
    @Test
    void findById() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(200L),
            LocalDateTime.now().withNano(0));

        //mocking
        when(voucherDBMemory.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        //when&then
        Optional<Voucher> byId = voucherService.findById(voucher.getVoucherId());
        assertEquals(byId.get(), voucher);
    }

    @DisplayName("바우처의 전체 리스트를 반환한다.")
    @Test
    void findAll() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(200L),
            LocalDateTime.now().withNano(0));
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), new DiscountRate(20L),
            LocalDateTime.now().withNano(0));

        //mocking
        when(voucherDBMemory.findAll()).thenReturn(List.of(voucher1, voucher2));

        //when&then
        List<Voucher> all = voucherService.findAll();
        assertThat(all).contains(voucher1, voucher2);
    }

    @DisplayName("해당id를 가진 바우처를 삭제한다")
    @Test
    void deleteById() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(200L),
            LocalDateTime.now().withNano(0));

        //mocking
        doNothing().when(voucherDBMemory).deleteById(voucher1.getVoucherId());

        //when&then
        voucherService.deleteById(voucher1.getVoucherId());
        verify(voucherDBMemory, times(1)).deleteById(voucher1.getVoucherId());

    }

}
