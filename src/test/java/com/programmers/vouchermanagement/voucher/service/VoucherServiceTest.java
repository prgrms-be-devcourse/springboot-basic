package com.programmers.vouchermanagement.voucher.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.AssignVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;

class VoucherServiceTest {
    VoucherRepository voucherRepository;
    CustomerRepository customerRepository;
    VoucherService voucherService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        voucherRepository = mock(VoucherRepository.class);
        voucherService = new VoucherService(voucherRepository);
    }
    
    @Test
    @DisplayName("고정 금액 바우처 생성에 성공한다.")
    void testFixedVoucherCreationSuccessful() {
        //given
        final CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal("100"), VoucherType.FIXED);

        //when
        final VoucherResponse voucherResponse = voucherService.create(request);

        //then
        assertThat(voucherResponse.isPercentVoucher(), is(false));

        //verify
        verify(voucherRepository).save(any(Voucher.class));
    }

    @Test
    @DisplayName("유효하지 않은 할인 값의 고정 금액 바우처 생성에 실패한다.")
    void testFixedVoucherCreationFailed_InvalidAmount() {
        //given
        final CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal("0"), VoucherType.FIXED);

        //when & then
        assertThatThrownBy(() -> voucherService.create(request)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("퍼센트 할인 바우처 생성에 성공한다.")
    void textPercentVoucherCreationSuccessful() {
        //given
        final CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal("50"), VoucherType.PERCENT);

        //when
        final VoucherResponse voucherResponse = voucherService.create(request);

        //then
        assertThat(voucherResponse.isPercentVoucher(), is(true));

        //verify
        verify(voucherRepository).save(any(Voucher.class));
    }

    @Test
    @DisplayName("유효하지 않은 할인율의 퍼센트 할인 바우처 생성에 실패한다.")
    void testPercentVoucherCreationFailed_InvalidPercent() {
        //given
        final CreateVoucherRequest firstRequest = new CreateVoucherRequest(new BigDecimal("0"), VoucherType.PERCENT);
        final CreateVoucherRequest secondRequest = new CreateVoucherRequest(new BigDecimal("100.1"), VoucherType.PERCENT);

        //when & then
        assertThatThrownBy(() -> voucherService.create(firstRequest)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> voucherService.create(secondRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("저장된 바우처가 없을 때 바우처 조회 시 빈 리스트를 반환한다.")
    void testListVouchersSuccessful_ReturnEmptyList() {
        //given
        doReturn(Collections.emptyList()).when(voucherRepository).findAll();

        //when
        final List<VoucherResponse> vouchers = voucherService.readAllVouchers();

        //then
        assertThat(vouchers.isEmpty(), is(true));

        //verify
        verify(voucherRepository).findAll();
    }

    @Test
    @DisplayName("저장된 바우처의 리스트를 읽는데 성공한다.")
    void testListVouchersSuccessful_ReturnList() {
        //given
        final Voucher firstVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        final Voucher secondVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(50), VoucherType.PERCENT);
        doReturn(List.of(firstVoucher, secondVoucher)).when(voucherRepository).findAll();

        //when
        final List<VoucherResponse> vouchers = voucherService.readAllVouchers();

        //then
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers, hasSize(2));

        //verify
        verify(voucherRepository).findAll();
    }

    @Test
    @DisplayName("존재하지 않는 바우처의 할당을 실패한다.")
    void testAssignVoucherFailed_NonExistentVoucher() {
        //given
        final AssignVoucherRequest request = new AssignVoucherRequest(UUID.randomUUID(), UUID.randomUUID());
        final NoSuchElementException exception = new NoSuchElementException();
        doReturn(false).when(voucherRepository).existById(request.voucherId());

        //when & then
        assertThatThrownBy(() -> voucherService.assignToCustomer(request)).isInstanceOf(NoSuchElementException.class);

        //verify
        verify(voucherRepository).existById(request.voucherId());
    }
}
