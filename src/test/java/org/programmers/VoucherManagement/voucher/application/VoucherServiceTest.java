package org.programmers.VoucherManagement.voucher.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.programmers.VoucherManagement.voucher.domain.*;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherResponse;
import org.programmers.VoucherManagement.voucher.dto.UpdateVoucherRequest;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("Fixed 바우처를 수정할 수 있다. - 성공")
    void updateFixedVoucher_Dto_Success() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher saveVoucher = new FixedAmountVoucher(voucherId, DiscountType.FIXED, new DiscountValue(100));
        voucherRepository.save(saveVoucher);
        UpdateVoucherRequest updateRequestDto = new UpdateVoucherRequest(1000);

        //mocking
        given(voucherRepository.findById(voucherId)).willReturn(Optional.ofNullable(saveVoucher));

        //when
        voucherService.updateVoucher(voucherId, updateRequestDto);

        //then
        Voucher updateVoucher = voucherRepository.findById(voucherId).get();
        assertThat(updateVoucher.getDiscountValue().getValue()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Percent 바우처를 수정할 수 있다. - 성공")
    void updatePercentVoucher_Dto_Success() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher saveVoucher = new PercentAmountVoucher(voucherId, DiscountType.PERCENT, new DiscountValue(10));
        voucherRepository.save(saveVoucher);
        UpdateVoucherRequest updateRequestDto = new UpdateVoucherRequest(40);

        //mocking
        given(voucherRepository.findById(voucherId)).willReturn(Optional.ofNullable(saveVoucher));

        //when
        voucherService.updateVoucher(voucherId, updateRequestDto);

        //then
        Voucher updateVoucher = voucherRepository.findById(voucherId).get();
        assertThat(updateVoucher.getDiscountValue().getValue()).isEqualTo(40);
    }

    @Test
    @DisplayName("Percent 바우처를 수정할 수 있다.(1-100을 넘어가면 예외 발생) - 실패")
    void updatePercentVoucher_Dto_ThrowVoucherException() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher saveVoucher = new PercentAmountVoucher(voucherId, DiscountType.PERCENT, new DiscountValue(10));
        voucherRepository.save(saveVoucher);
        UpdateVoucherRequest updateRequestDto = new UpdateVoucherRequest(-1);

        //mocking
        given(voucherRepository.findById(voucherId)).willReturn(Optional.ofNullable(saveVoucher));

        //then
        assertThatThrownBy(() -> voucherService.updateVoucher(voucherId, updateRequestDto))
                .isInstanceOf(VoucherException.class)
                .hasMessage("할인율은 1부터 100사이의 값이여야 합니다.");
    }

    @Test
    @DisplayName("바우처ID를 입력받아 해당 바우처를 삭제할 수 있다. - 성공")
    void deleteVoucher_voucherId_Success() {
        //given
        Voucher saveVoucher = new FixedAmountVoucher(UUID.randomUUID(), DiscountType.FIXED, new DiscountValue(1000));
        voucherRepository.save(saveVoucher);

        //mocking
        given(voucherRepository.findById(saveVoucher.getVoucherId())).willReturn(Optional.ofNullable(saveVoucher));

        //when
        voucherService.deleteVoucher(saveVoucher.getVoucherId());

        //then
        verify(voucherRepository, times(1)).findById(saveVoucher.getVoucherId());
        verify(voucherRepository, times(1)).delete(any(Voucher.class));
    }

    @Test
    @DisplayName("바우처 전체 목록을 가져올 수 있다. - 성공")
    void getVouchers_EqualsListOfVouchers() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), DiscountType.FIXED, new DiscountValue(100));
        Voucher voucher2 = new PercentAmountVoucher(UUID.randomUUID(), DiscountType.PERCENT, new DiscountValue(10));
        List<Voucher> voucherList = Arrays.asList(voucher1, voucher2);

        //mocking
        given(voucherRepository.findAll()).willReturn(voucherList);

        //when
        GetVoucherListResponse response = voucherService.getVoucherList();

        //then
        assertThat(response).isNotNull();
        List<GetVoucherResponse> responseExpect = voucherList.stream()
                .map(GetVoucherResponse::toDto)
                .collect(Collectors.toList());
        assertThat(response.getGetVoucherListRes()).containsExactlyElementsOf(responseExpect);
    }
}
