package com.programmers.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import com.programmers.domain.voucher.FixedAmountVoucher;
import com.programmers.domain.voucher.PercentDiscountVoucher;
import com.programmers.domain.voucher.Voucher;
import com.programmers.domain.voucher.VoucherType;
import com.programmers.domain.voucher.dto.VoucherCreateRequestDto;
import com.programmers.domain.voucher.dto.VoucherResponseDto;
import com.programmers.domain.voucher.dto.VoucherUpdateRequestDto;
import com.programmers.domain.voucher.dto.VouchersResponseDto;
import com.programmers.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class VoucherServiceTest {

    @Mock
    private VoucherRepository voucherRepository;

    private VoucherService voucherService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        voucherService = new VoucherService(voucherRepository);
    }

    @DisplayName("바우처를 저장한다")
    @Test
    public void save() {
        //given
        VoucherCreateRequestDto voucherCreateRequestDto = new VoucherCreateRequestDto("TestVoucher", 100L, VoucherType.FixedAmountVoucher);
        FixedAmountVoucher savedVoucher = new FixedAmountVoucher(UUID.randomUUID(), "TestVoucher", 100L);

        when(voucherRepository.save(any(FixedAmountVoucher.class))).thenReturn(savedVoucher);

        //when
        VoucherResponseDto voucherResponseDto = voucherService.save(voucherCreateRequestDto);

        //then
        assertThat(savedVoucher.getVoucherId(), is(voucherResponseDto.id()));
        assertThat(savedVoucher.getVoucherName(), is(voucherResponseDto.name()));
        assertThat(savedVoucher.getVoucherValue(), is(voucherResponseDto.value()));
        assertThat(savedVoucher.getVoucherType(), is(voucherResponseDto.type()));
    }

    @DisplayName("저장된 모든 바우처를 조회한다")
    @Test
    public void findAll() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "voucher1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "voucher2", 20L);
        List<Voucher> vouchers = Arrays.asList(fixedAmountVoucher, percentDiscountVoucher);

        when(voucherRepository.findAll()).thenReturn(vouchers);

        //when
        VouchersResponseDto vouchersResponseDto = voucherService.findAll();

        //then
        assertThat(vouchers.size(), is(vouchersResponseDto.vouchers().size()));
        assertThat(fixedAmountVoucher.getVoucherId(), is(vouchersResponseDto.vouchers().get(0).getVoucherId()));
        assertThat(fixedAmountVoucher.getVoucherName(), is(vouchersResponseDto.vouchers().get(0).getVoucherName()));
        assertThat(percentDiscountVoucher.getVoucherValue(), is(vouchersResponseDto.vouchers().get(1).getVoucherValue()));
    }

    @DisplayName("id로 바우처를 조회한다")
    @Test
    public void findById() {
        //given
        UUID id = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(id, "TestVoucher", 10L);

        when(voucherRepository.findById(id)).thenReturn(Optional.of(fixedAmountVoucher));

        //when
        VoucherResponseDto response = voucherService.findById(id);

        //then
        assertThat(fixedAmountVoucher.getVoucherId(), is(response.id()));
        assertThat(fixedAmountVoucher.getVoucherName(), is(response.name()));
    }

    @DisplayName("바우처를 업데이트한다")
    @Test
    public void update() {
        //given
        UUID id = UUID.randomUUID();
        VoucherUpdateRequestDto voucherDto = new VoucherUpdateRequestDto(id, "voucher", 10L, VoucherType.FixedAmountVoucher);
        Voucher updatedVoucher = new FixedAmountVoucher(id, "updatedVoucher", 20L);

        when(voucherRepository.update(any(FixedAmountVoucher.class))).thenReturn(updatedVoucher);

        //when
        VoucherResponseDto response = voucherService.update(voucherDto);

        //then
        assertThat(updatedVoucher.getVoucherId(), is(response.id()));
        assertThat(updatedVoucher.getVoucherName(), is(response.name()));
        assertThat(updatedVoucher.getVoucherValue(), is(response.value()));
    }
}