package kr.co.springbootweeklymission.voucher.application;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.voucher.api.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.creators.VoucherCreators;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import kr.co.springbootweeklymission.voucher.domain.repository.VoucherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {
    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    @Test
    void getVoucherById_특정_바우처를_조회_SUCCESS() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();
        given(voucherRepository.findById(any())).willReturn(Optional.of(voucher));

        //when & then
        assertThat(voucherService.getVoucherById(voucher.getVoucherId()).getVoucherId())
                .isEqualTo(voucher.getVoucherId());
    }

    @Test
    void getVoucherById_특정_바우처를_조회_NotFoundException() {
        //given
        given(voucherRepository.findById(any())).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> voucherService.getVoucherById(UUID.randomUUID()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ResponseStatus.FAIL_NOT_FOUND_VOUCHER.getMessage());
    }

    @Test
    void 특정_바우처의_정보를_변경_SUCCESS() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();
        VoucherReqDTO.UPDATE update = VoucherCreators.updateVoucherInformation(20, VoucherPolicy.PERCENT_DISCOUNT);
        given(voucherRepository.findById(any())).willReturn(Optional.of(voucher));

        //when
        voucherService.updateVoucherById(voucher.getVoucherId(), update);

        //then
        assertThat(voucher.getVoucherPolicy()).isEqualTo(update.getVoucherPolicy());
    }
}
