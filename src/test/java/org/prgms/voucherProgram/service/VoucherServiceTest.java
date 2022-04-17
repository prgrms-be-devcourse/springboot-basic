package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucherProgram.domain.voucher.FixedAmountVoucher;
import org.prgms.voucherProgram.domain.voucher.PercentDiscountVoucher;
import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.dto.VoucherDto;
import org.prgms.voucherProgram.exception.VoucherIsNotExistsException;
import org.prgms.voucherProgram.repository.voucher.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherService voucherService;

    private static Stream<Arguments> provideVoucher() {
        return Stream.of(
            Arguments.of(new VoucherDto(UUID.randomUUID(), 1, 10L)),
            Arguments.of(new VoucherDto(UUID.randomUUID(), 2, 30L))
        );
    }

    @DisplayName("바우처를 저장한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void should_ReturnVoucher_When_createVoucher(VoucherDto voucherDto) {
        given(voucherRepository.save(any(Voucher.class))).willReturn(voucherDto.toEntity());

        VoucherDto newVoucher = voucherService.create(voucherDto);

        assertThat(newVoucher).usingRecursiveComparison()
            .isEqualTo(voucherDto);
        then(voucherRepository).should(times(1)).save(any(Voucher.class));
    }

    @DisplayName("수정할 바우처가 존재한다면 바우처를 수정한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void sholud_ReturnUpdateVoucher_When_VoucherIsExists(VoucherDto voucherDto) {
        // given
        given(voucherRepository.findById(voucherDto.getVoucherId())).willReturn(Optional.of(voucherDto.toEntity()));
        voucherDto.setType(2);
        voucherDto.setDiscountValue(50);
        given(voucherRepository.update(any(Voucher.class))).willReturn(voucherDto.toEntity());

        // when
        VoucherDto updateVoucher = voucherService.update(voucherDto);

        // then
        assertThat(updateVoucher).usingRecursiveComparison()
            .isEqualTo(voucherDto);
        then(voucherRepository).should(times(1)).findById(any(UUID.class));
        then(voucherRepository).should(times(1)).update(any(Voucher.class));
    }

    @DisplayName("수정할 바우처가 없다면 예외를 발생한다.")
    @ParameterizedTest
    @MethodSource("provideVoucher")
    void should_ThrowException_When_VoucherIsNotExists(VoucherDto voucherDto) {
        // given
        given(voucherRepository.findById(voucherDto.getVoucherId())).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> voucherService.update(voucherDto))
            .isInstanceOf(VoucherIsNotExistsException.class)
            .hasMessage("[ERROR] 해당 아이디로 저장된 바우처가 없습니다.");
        then(voucherRepository).should(times(1)).findById(voucherDto.getVoucherId());
        then(voucherRepository).should(times(0)).update(any(Voucher.class));
    }

    @DisplayName("모든 바우처를 반환한다.")
    @Test
    void findAllVoucher_ReturnAllVoucher() {
        Voucher voucherOne = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Voucher voucherTwo = new PercentDiscountVoucher(UUID.randomUUID(), UUID.randomUUID(), 20L);
        List<Voucher> mockVouchers = List.of(voucherOne, voucherTwo);
        given(voucherRepository.findAll()).willReturn(mockVouchers);

        List<VoucherDto> vouchers = voucherService.findAllVoucher();

        assertThat(vouchers).hasSize(2)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
            .contains(VoucherDto.from(voucherOne), VoucherDto.from(voucherTwo));
        then(voucherRepository).should(times(1)).findAll();
    }
}
