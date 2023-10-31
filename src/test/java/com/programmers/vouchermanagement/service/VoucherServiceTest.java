package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.VoucherDto;
import com.programmers.vouchermanagement.message.ErrorMessage;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    @InjectMocks
    private VoucherService voucherService;
    @Mock
    private VoucherRepository voucherRepository;
    private final List<Voucher> testVouchers = new ArrayList<>();
    private final Voucher voucher1 = VoucherFactory.createVoucher(new VoucherDto.CreateRequest("voucher1", 1000, VoucherType.FIXED));
    private final Voucher voucher2 = VoucherFactory.createVoucher(new VoucherDto.CreateRequest("voucher2", 99, VoucherType.PERCENTAGE));

    @Test
    void 모든_바우처를_가져올_수_있다() {
        //given
        testVouchers.add(voucher1);
        testVouchers.add(voucher2);
        doReturn(testVouchers).when(voucherRepository).findAll();

        //when
        final List<Voucher> allVouchers = voucherService.findAllVouchers();

        //then
        assertThat(allVouchers).hasSameSizeAs(testVouchers);
    }

    @Test
    void ID로_바우처를_가져올_수_있다() {
        //given
        doReturn(Optional.of(voucher1)).when(voucherRepository).findById(voucher1.getId());

        //when
        final Voucher voucher = voucherService.findByVoucherId(voucher1.getId());

        //then
        assertThat(voucher).isEqualTo(voucher1);
    }

    @Test
    void 이름에_특정문자열이_포함된_바우처를_가져올_수_있다() {
        //given
        final List<Voucher> singletonCustomers = Collections.singletonList(voucher2);
        doReturn(singletonCustomers).when(voucherRepository).findByNameLike("2");

        //when
        final List<Voucher> vouchers = voucherService.findByVoucherName("2");

        //then
        assertThat(vouchers).hasSameSizeAs(singletonCustomers).contains(voucher2);
    }

    @Test
    void 새로운_바우처를_생성할_수_있다() {
        //given
        final VoucherDto.CreateRequest createRequestDto = new VoucherDto.CreateRequest("voucher", 999, VoucherType.FIXED);
        final Voucher voucher = VoucherFactory.createVoucher(createRequestDto);
        doReturn(voucher).when(voucherRepository).save(any(Voucher.class));

        //when
        final Voucher createdVoucher = voucherService.createVoucher(createRequestDto);

        //then
        assertThat(createdVoucher).isEqualTo(voucher);
    }

    @Test
    void 바우처의_이름은_중복될_수_없다() {
        //given
        final VoucherDto.CreateRequest createRequestDto = new VoucherDto.CreateRequest("voucher1", 999, VoucherType.FIXED);
        doReturn(Optional.of(voucher1)).when(voucherRepository).findByName(voucher1.getName());

        //when&then
        assertThatThrownBy(() -> voucherService.createVoucher(createRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.VOUCHER_ALREADY_EXISTS_MESSAGE.getMessage());
    }

    @Test
    void 존재하지_않는_바우처를_삭제할_수_없다() {
        //given
        doReturn(0).when(voucherRepository).delete(any(UUID.class));

        //when&then
        assertThatThrownBy(() -> voucherService.deleteVoucher(UUID.randomUUID()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining(ErrorMessage.VOUCHER_NOT_FOUND_MESSAGE.getMessage());
    }
}
