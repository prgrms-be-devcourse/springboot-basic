package com.programmers.voucher.domain.voucher.service;

import com.programmers.voucher.domain.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.dto.VoucherResponse;
import com.programmers.voucher.domain.voucher.dto.VoucherUpdateRequest;
import com.programmers.voucher.domain.voucher.entity.Voucher;
import com.programmers.voucher.domain.voucher.entity.VoucherType;
import com.programmers.voucher.domain.voucher.repository.VoucherRepository;
import com.programmers.voucher.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    @Mock
    private VoucherRepository voucherRepository;
    @InjectMocks
    private VoucherService voucherService;

    @Test
    @DisplayName("바우처 생성에 성공한다.")
    void 바우처_생성_성공() {
        // given
        VoucherCreateRequest request = new VoucherCreateRequest(VoucherType.FIXED, 100);
        Voucher voucher = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.FIXED).amount(100).build();
        given(voucherRepository.insert(any(Voucher.class))).willReturn(voucher);

        // when
        VoucherResponse response = voucherService.createVoucher(request);

        // then
        assertThat(response.voucherId()).isEqualTo(voucher.getId());
    }

    @Test
    @DisplayName("모든 바우처 조회에 성공한다.")
    void 모든_바우처_조회_성공() {
        // given
        Voucher voucher1 = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.FIXED).amount(100).build();
        Voucher voucher2 = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.PERCENT).amount(20).build();
        List<Voucher> vouchers = List.of(voucher1, voucher2);
        given(voucherRepository.findAll()).willReturn(vouchers);

        // when
        List<VoucherResponse> response = voucherService.getAllVouchers();

        // then
        assertThat(response).hasSize(2);
    }

    @Test
    @DisplayName("바우처 조회에 성공한다.")
    void 바우처_조회_성공() {
        // given
        Voucher voucher = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.FIXED).amount(100).build();
        given(voucherRepository.findById(voucher.getId())).willReturn(Optional.of(voucher));

        // when
        VoucherResponse response = voucherService.getVoucher(voucher.getId());

        // then
        assertThat(response.voucherType()).isEqualTo(voucher.getType());
        assertThat(response.amount()).isEqualTo(voucher.getAmount());
    }

    @Test
    @DisplayName("존재하지 않는 바우처를 조회한 경우 예외가 발생한다.")
    void 바우처_조회_예외() {
        // given
        UUID voucherId = UUID.randomUUID();
        given(voucherRepository.findById(voucherId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> voucherService.getVoucher(voucherId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하는 바우처가 없습니다.");
    }

    @Test
    @DisplayName("바우처 수정에 성공한다.")
    void 바우처_수정_성공() {
        // given
        VoucherUpdateRequest request = new VoucherUpdateRequest(VoucherType.PERCENT, 20);
        Voucher voucher = Voucher.builder().id(UUID.randomUUID()).type(VoucherType.FIXED).amount(100).build();
        given(voucherRepository.findById(voucher.getId())).willReturn(Optional.of(voucher));
        given(voucherRepository.update(voucher)).willReturn(voucher);

        // when
        VoucherResponse response = voucherService.updateVoucher(voucher.getId(), request);

        // then
        assertThat(response.voucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(response.amount()).isEqualTo(20);
    }
}
