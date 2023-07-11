package org.prgrms.kdtspringdemo.voucher.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponseDto;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.PercentAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.prgrms.kdtspringdemo.voucher.ropository.VoucherRepository;
import org.prgrms.kdtspringdemo.voucher.service.VoucherServiceImpl;

import java.util.List;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    @InjectMocks
    private VoucherServiceImpl voucherService;

    @Mock
    private VoucherRepository voucherRepository;

    @Test
    void 바우처_생성_성공_테스트() {
        //given
        long amount = 1000;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(amount);

        //mocking
        given(voucherRepository.save(any())).willReturn(fixedAmountVoucher);

        //when
        VoucherResponseDto responseDto = voucherService.create(fixedAmountVoucher.getVoucherType(), fixedAmountVoucher.getAmount());

        //then
        Assertions.assertThat(responseDto.getVoucherId()).isEqualTo(fixedAmountVoucher.getVoucherId());
    }

    @Test
    void 바우처_조회_성공_테스트() {
        //given
        long amount = 1000;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(amount);

        //mocking
        given(voucherRepository.findById(any())).willReturn(fixedAmountVoucher);

        //when
        VoucherResponseDto responseDto = voucherService.findById(fixedAmountVoucher.getVoucherId());

        //then
        Assertions.assertThat(responseDto.getAmount()).isEqualTo(fixedAmountVoucher.getAmount());
    }

    @Test
    void 바우처_모두_조회_성공_테스트() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1000);
        PercentAmountVoucher percentAmountVoucher = new PercentAmountVoucher(10);
        List<Voucher> vouchers = List.of(fixedAmountVoucher, percentAmountVoucher);
        //mocking
        given(voucherRepository.findAll()).willReturn(vouchers);

        //when
        List<VoucherResponseDto> responseDtos = voucherService.findAll();

        //then
        Assertions.assertThat(responseDtos).hasSize(2);
    }
}
