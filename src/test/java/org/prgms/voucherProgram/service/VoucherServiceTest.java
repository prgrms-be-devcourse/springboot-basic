package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucherProgram.domain.voucher.FixedAmountVoucher;
import org.prgms.voucherProgram.domain.voucher.PercentDiscountVoucher;
import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.dto.VoucherDto;
import org.prgms.voucherProgram.repository.voucher.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherService voucherService;

    @DisplayName("FixedAmountVoucerType을 주면 FixedAmountVoucher를 반환한다.")
    @Test
    void create_FixedAmountVoucherType_ReturnFixedAmountVoucher() {

        VoucherDto voucherDto = new VoucherDto(UUID.randomUUID(), UUID.randomUUID(), 1, 100L);
        given(voucherRepository.save(any(Voucher.class))).willReturn(voucherDto.toEntity());

        VoucherDto newVoucher = voucherService.create(voucherDto);

        assertThat(newVoucher).usingRecursiveComparison()
            .isEqualTo(voucherDto);
        then(voucherRepository).should(times(1)).save(any(Voucher.class));
    }

    @DisplayName("PercentDiscountVoucerType을 주면 PercentDiscountVoucher를 반환한다.")
    @Test
    void create_PercentDiscountType_ReturnPercentDiscountVoucher() {
        VoucherDto voucherDto = new VoucherDto(UUID.randomUUID(), UUID.randomUUID(), 2, 100L);
        given(voucherRepository.save(any(Voucher.class))).willReturn(voucherDto.toEntity());

        VoucherDto newVoucher = voucherService.create(voucherDto);

        assertThat(newVoucher).usingRecursiveComparison()
            .isEqualTo(voucherDto);
        then(voucherRepository).should(times(1)).save(any(Voucher.class));
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
