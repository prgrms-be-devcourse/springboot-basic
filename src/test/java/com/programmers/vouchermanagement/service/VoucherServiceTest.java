package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.voucher.request.CreateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.request.UpdateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.response.VoucherResponseDto;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

class VoucherServiceTest {

    private VoucherRepository voucherRepository;
    private VoucherService voucherService;

    public VoucherServiceTest() {
        this.voucherRepository = Mockito.mock(VoucherRepository.class);
        this.voucherService = new VoucherService(voucherRepository);
    }

    @Test
    @DisplayName("FIXED_AMOUNT 바우처를 생성할 수 있다.")
    void createFixedAmountVoucher() {
        // given
        CreateVoucherRequestDto request = new CreateVoucherRequestDto();
        request.setType(VoucherType.FIXED_AMOUNT);
        request.setAmount(1000L);

        // when
        voucherService.createVoucher(request);

        // then
        verify(voucherRepository).save(any(FixedAmountVoucher.class));

    }

    @Test
    @DisplayName("PERCENT_DISCOUNT 바우처를 생성할 수 있다.")
    void createPercentDiscountVoucher() {
        // given
        CreateVoucherRequestDto request = new CreateVoucherRequestDto();
        request.setType(VoucherType.PERCENT_DISCOUNT);
        request.setAmount(10L);

        // when
        voucherService.createVoucher(request);

        // then
        verify(voucherRepository).save(any(PercentDiscountVoucher.class));
    }

    @Test
    @DisplayName("바우처 목록을 조회할 수 있다.")
    void getVouchers() {
        // given
        List<Voucher> mockVouchers = Arrays.asList(
                new FixedAmountVoucher(1000L),
                new PercentDiscountVoucher(10L));
        given(voucherRepository.findAll()).willReturn(mockVouchers);

        // when
        List<VoucherResponseDto> vouchers = voucherService.getVouchers();

        // then
        verify(voucherRepository).findAll();
        assertThat(vouchers).hasSize(2);
        assertThat(vouchers).extracting(VoucherResponseDto::getType)
                .containsExactlyInAnyOrder(VoucherType.FIXED_AMOUNT, VoucherType.PERCENT_DISCOUNT);
        assertThat(vouchers).extracting(VoucherResponseDto::getAmount)
                .containsExactlyInAnyOrder(1000L, 10L);
    }

    @Test
    @DisplayName("바우처의 아이디로 바우처를 조회할 수 있다.")
    void getVoucher() {
        // given
        Voucher mockVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        given(voucherRepository.findById(mockVoucher.getId())).willReturn(java.util.Optional.of(mockVoucher));

        // when
        VoucherResponseDto voucher = voucherService.getVoucher(mockVoucher.getId());

        // then
        verify(voucherRepository).findById(mockVoucher.getId());
        assertThat(voucher.getType()).isEqualTo(VoucherType.FIXED_AMOUNT);
        assertThat(voucher.getAmount()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("존재하지 않는 바우처의 아이디로 조회 요청 시 실패한다.")
    void getVoucher_notFound_fail() {
        // when & then
        assertThatThrownBy(() -> voucherService.getVoucher(UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not found voucher");
    }

    @Test
    @DisplayName("바우처를 수정할 수 있다.")
    void updateVoucher() {
        // given
        Voucher mockVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        given(voucherRepository.findById(mockVoucher.getId())).willReturn(java.util.Optional.of(mockVoucher));

        UpdateVoucherRequestDto request = new UpdateVoucherRequestDto();
        request.setAmount(2000L);

        // when
        voucherService.updateVoucher(mockVoucher.getId(), request);

        // then
        verify(voucherRepository).update(any(FixedAmountVoucher.class));
    }

    @Test
    @DisplayName("존재하지 않는 바우처의 아이디로 수정 요청 시 실패한다.")
    void updateVoucher_notFound_fail() {
        // given
        UpdateVoucherRequestDto request = new UpdateVoucherRequestDto();
        request.setAmount(2000L);

        // when & then
        assertThatThrownBy(() -> voucherService.updateVoucher(UUID.randomUUID(), request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not found voucher");
    }

    @Test
    @DisplayName("바우처를 삭제할 수 있다.")
    void deleteVoucher() {
        // given
        Voucher mockVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);

        // when
        voucherService.deleteVoucher(mockVoucher.getId());

        // then
        verify(voucherRepository).deleteById(mockVoucher.getId());
    }
}
