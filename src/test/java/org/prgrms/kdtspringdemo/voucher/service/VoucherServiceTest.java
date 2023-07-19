package org.prgrms.kdtspringdemo.voucher.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdtspringdemo.voucher.exception.VoucherIdNotFoundException;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponse;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.PercentAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.ropository.VoucherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    @InjectMocks
    private VoucherServiceImpl voucherService;

    @Mock
    private VoucherRepository voucherRepository;

    @Test
    void 바우처_생성_성공_테스트() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(1000);

        //mocking
        given(voucherRepository.save(any())).willReturn(voucher);

        //when
        VoucherResponse response = voucherService.create(voucher.getVoucherType(), voucher.getAmount());

        //then
        assertThat(response.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    void 바우처_조회_성공_테스트() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(1000);

        //mocking
        given(voucherRepository.findById(any())).willReturn(Optional.of(voucher));

        //when
        VoucherResponse response = voucherService.findById(voucher.getVoucherId());

        //then
        assertThat(response.getAmount()).isEqualTo(voucher.getAmount());
    }

    @Test
    void 바우처_조회_실패_테스트() {
        //given
        UUID voucherId = UUID.randomUUID();

        //mocking
        given(voucherRepository.findById(voucherId)).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> voucherService.findById(voucherId))
                .isInstanceOf(VoucherIdNotFoundException.class)
                .hasMessage(VOUCHER_ID_LOOKUP_FAILED.getMessage());
    }

    @Test
    void 바우처_모두_조회_성공_테스트() {
        //given
        FixedAmountVoucher voucher1 = new FixedAmountVoucher(1000);
        PercentAmountVoucher voucher2 = new PercentAmountVoucher(10);
        List<Voucher> vouchers = List.of(voucher1, voucher2);

        //mocking
        given(voucherRepository.findAll()).willReturn(vouchers);

        //when
        List<VoucherResponse> responses = voucherService.findAll();

        //then
        assertThat(responses).hasSize(2);
    }

    @Test
    void 바우처_수정_성공_테스트() {
        //given
        Voucher voucher = new FixedAmountVoucher(1000);
        given(voucherRepository.save(any())).willReturn(voucher);
        VoucherResponse savedVoucher = voucherService.create(voucher.getVoucherType(), voucher.getAmount());
        PercentAmountVoucher updateVoucher = new PercentAmountVoucher(savedVoucher.getVoucherId(), 10);

        //mocking
        doNothing().when(voucherRepository).update(any(), any(), anyLong());

        //then
        voucherService.update(savedVoucher.getVoucherId(), updateVoucher.getVoucherType(), updateVoucher.getAmount());

        //when
        verify(voucherRepository, times(1)).update(updateVoucher.getVoucherId(), updateVoucher.getVoucherType(), updateVoucher.getAmount());
    }
}
