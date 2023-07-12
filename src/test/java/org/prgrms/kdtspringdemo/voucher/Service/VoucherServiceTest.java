package org.prgrms.kdtspringdemo.voucher.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
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
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1000);

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
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1000);

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

    @Test
    void 바우처_수정_성공_테스트() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(1000);
        given(voucherRepository.save(any())).willReturn(voucher1);
        VoucherResponseDto voucher2 = voucherService.create(voucher1.getVoucherType(), voucher1.getAmount());

        VoucherType percent = VoucherType.PERCENT;
        long amount = 10;
        PercentAmountVoucher voucher3 = new PercentAmountVoucher(voucher2.getVoucherId(), amount);

        //mocking
        given(voucherRepository.update(any())).willReturn(voucher3);

        //when
        VoucherResponseDto responseDto = voucherService.update(voucher2.getVoucherId(), percent, amount);

        //then
        Assertions.assertThat(responseDto.getVoucherId()).isEqualTo(voucher1.getVoucherId());
        Assertions.assertThat(responseDto.getVoucherType()).isEqualTo(percent);
        Assertions.assertThat(responseDto.getAmount()).isEqualTo(amount);

    }
}
