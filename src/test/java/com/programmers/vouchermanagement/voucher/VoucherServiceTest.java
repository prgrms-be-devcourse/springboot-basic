package com.programmers.vouchermanagement.voucher;

import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.VoucherRequestDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotFoundException;
import com.programmers.vouchermanagement.voucher.mapper.VoucherPolicyMapper;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {

    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("모든 Voucher를 조회할 수 있다.")
    void successReadAllVoucher() {

        // given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(10000L, VoucherType.FIXED));
        Voucher voucher2 = new Voucher(UUID.randomUUID(), VoucherType.PERCENT, VoucherPolicyMapper.toEntity(80L, VoucherType.PERCENT));
        Voucher voucher3 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(500L, VoucherType.FIXED));
        List<Voucher> vouchers = new ArrayList<>() {{
            add(voucher1);
            add(voucher2);
            add(voucher3);
        }};
        when(voucherRepository.findAll()).thenReturn(vouchers);

        // when
        List<VoucherResponseDto> voucherResponseDtos = voucherService.readAllVoucher();

        // then
        assertThat(voucherResponseDtos).hasSize(3)
                .extracting(VoucherResponseDto::getVoucherId)
                .contains(voucher1.getVoucherId(), voucher2.getVoucherId(), voucher3.getVoucherId());
    }

    @Test
    @DisplayName("존재하지 않는 아이디는 Voucher 조회 시 예외를 발생시킨다.")
    void failReadVoucherById() {

        // given
        UUID voucherId = UUID.randomUUID();
        when(voucherRepository.findById(voucherId)).thenThrow(new VoucherNotFoundException());

        // when - then
        assertThatThrownBy(() -> voucherService.readVoucherById(voucherId))
                .isInstanceOf(VoucherNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 아이디는 Voucher 수정 시 예외를 발생시킨다.")
    void failUpdateVoucher() {

        // given
        UUID voucherId = UUID.randomUUID();
        when(voucherRepository.findById(voucherId)).thenThrow(new VoucherNotFoundException());

        // when - then
        assertThatThrownBy(() -> voucherService.updateVoucher(voucherId, new VoucherRequestDto("fixed", 500L)))
                .isInstanceOf(VoucherNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 아이디는 Voucher 삭제 시 예외를 발생시킨다.")
    void failRemoveVoucherById() {

        // given
        UUID voucherId = UUID.randomUUID();
        when(voucherRepository.findById(voucherId)).thenThrow(new VoucherNotFoundException());

        // when - then
        assertThatThrownBy(() -> voucherService.removeVoucherById(voucherId))
                .isInstanceOf(VoucherNotFoundException.class);
    }
}
