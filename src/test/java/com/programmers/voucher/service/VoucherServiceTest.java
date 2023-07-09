package com.programmers.voucher.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherType;
import com.programmers.voucher.dto.VoucherCreateRequestDto;
import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.dto.VouchersResponseDto;
import com.programmers.voucher.repository.VoucherRepository;
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
        VoucherDto voucherDto = voucherService.save(voucherCreateRequestDto);

        //then
        assertThat(savedVoucher.getVoucherId(), is(voucherDto.id()));
        assertThat(savedVoucher.getVoucherName(), is(voucherDto.name()));
        assertThat(savedVoucher.getVoucherValue(), is(voucherDto.value()));
        assertThat(savedVoucher.getVoucherType(), is(voucherDto.type()));
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
        VoucherDto voucherDto = voucherService.findById(id);

        //then
        assertThat(fixedAmountVoucher.getVoucherId(), is(voucherDto.id()));
        assertThat(fixedAmountVoucher.getVoucherName(), is(voucherDto.name()));
    }

    @DisplayName("바우처를 업데이트한다")
    @Test
    public void update() {
        //given
        UUID id = UUID.randomUUID();
        VoucherDto voucherDto = new VoucherDto(id, "voucher", 10L, VoucherType.FixedAmountVoucher, Optional.empty());
        Voucher updatedVoucher = new FixedAmountVoucher(id, "updatedVoucher", 20L);

        when(voucherRepository.update(any(FixedAmountVoucher.class))).thenReturn(updatedVoucher);

        //when
        VoucherDto result = voucherService.update(voucherDto);

        //then
        assertThat(updatedVoucher.getVoucherId(), is(result.id()));
        assertThat(updatedVoucher.getVoucherName(), is(result.name()));
        assertThat(updatedVoucher.getVoucherValue(), is(result.value()));
    }
}