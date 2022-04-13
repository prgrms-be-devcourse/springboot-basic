package org.prgrms.vouchermanager.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.vouchermanager.voucher.domain.Voucher;
import org.prgrms.vouchermanager.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class VoucherServiceImplTest {

    @Test
    @DisplayName("createVoucher는 VoucherRepository에 insert한다.")
    void createVoucherTest() {
        //given
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherServiceImpl(voucherRepository);

        //when
        voucherService.createVoucher("FIXED", 10L);

        //then
        verify(voucherRepository, times(1)).insert(any(Voucher.class));
    }

    @Test
    @DisplayName("존재하는 VoucherList를 출력한다.")
    void allVoucherToString() {
        //given
        Voucher voucher = mock(Voucher.class);
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherServiceImpl(voucherRepository);

        //when
        when(voucher.toString()).thenReturn("voucher info");
        when(voucherRepository.getAll()).thenReturn(List.of(voucher));

        //then
        assertEquals("voucher info\n", voucherService.allVouchersToString());
    }

    @Test
    @DisplayName("createVoucher에서 잘못된 타입을 입력 받았을 때, 예외를 던진다.")
    void testWithIllegalTypeArg() {
        //given
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherServiceImpl(voucherRepository);

        //then
        assertThrows(IllegalArgumentException.class, () ->
                voucherService.createVoucher("fix2", 10L)
        );
    }

    @Test
    @DisplayName("저장되지 않은 voucherId로 findVoucher 경우 예외를 던진다.")
    void findVoucher_test() {
        //given
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherServiceImpl(voucherRepository);

        //when
        when(voucherRepository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThrows(IllegalArgumentException.class, () -> voucherService.findVoucher(UUID.randomUUID()));
    }

}