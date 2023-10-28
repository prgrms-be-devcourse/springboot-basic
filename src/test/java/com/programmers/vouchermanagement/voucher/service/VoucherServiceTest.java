package com.programmers.vouchermanagement.voucher.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
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

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.VoucherCustomerRequest;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
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
        voucherService = new VoucherService(voucherRepository, customerRepository);
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
    @DisplayName("존재하지 않는 고객에게 바우처 할당을 실패한다.")
    void testAssignVoucherFailed_NonExistentCustomer() {
        final VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        final NoSuchElementException exception = new NoSuchElementException();
        doReturn(false).when(customerRepository).existById(request.customerId());

        //when & then
        assertThatThrownBy(() -> voucherService.grantToCustomer(request)).isInstanceOf(NoSuchElementException.class);

        //verify
        verify(customerRepository).existById(request.customerId());
    }

    @Test
    @DisplayName("존재하지 않는 바우처의 할당을 실패한다.")
    void testAssignVoucherFailed_NonExistentVoucher() {
        //given
        final VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        final NoSuchElementException exception = new NoSuchElementException();
        doReturn(true).when(customerRepository).existById(request.customerId());
        doReturn(Optional.empty()).when(voucherRepository).findById(any(UUID.class));

        //when & then
        assertThatThrownBy(() -> voucherService.grantToCustomer(request)).isInstanceOf(NoSuchElementException.class);

        //verify
        verify(voucherRepository).findById(request.voucherId());
    }

    @Test
    @DisplayName("바우처 할당을 성공한다.")
    void testAssignVoucherSuccessful() {
        //given
        final VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        final Voucher voucher = new Voucher(request.voucherId(), new BigDecimal(10000), VoucherType.FIXED, request.customerId());
        doReturn(true).when(customerRepository).existById(request.customerId());
        doReturn(Optional.of(voucher)).when(voucherRepository).findById(request.voucherId());
        doReturn(voucher).when(voucherRepository).save(any(Voucher.class));

        //when
        voucherService.grantToCustomer(request);

        //verify
        verify(voucherRepository).findById(request.voucherId());
        verify(voucherRepository).save(any(Voucher.class));
    }

    @Test
    @DisplayName("존재하지 않는 고객의 바우처 정보 조회를 실패한다.")
    void testFindVouchersByCustomerIdFailed_NonExistentCustomer() {
        //given
        final NoSuchElementException exception = new NoSuchElementException();
        final UUID customerId = UUID.randomUUID();
        doThrow(exception).when(customerRepository).findById(any(UUID.class));

        //when && then
        assertThatThrownBy(() -> voucherService.findByCustomerId(customerId));

        //verify
        verify(customerRepository).findById(customerId);
    }

    @Test
    @DisplayName("검색한 고객에게 할당된 바우처가 없으면 빈 리스트를 반환한다.")
    void testFindVouchersByCustomerIdSuccessful_ReturnEmptyList() {
        //given
        final Customer customer = new Customer(UUID.randomUUID(), "test-customer", CustomerType.NORMAL);
        doReturn(Optional.of(customer)).when(customerRepository).findById(any(UUID.class));
        doReturn(Collections.emptyList()).when(voucherRepository).findByCustomerId(any(UUID.class));

        //when
        List<VoucherResponse> vouchers = voucherService.findByCustomerId(customer.getCustomerId());

        //then
        assertThat(vouchers.isEmpty(), is(true));

        //verify
        verify(voucherRepository).findByCustomerId(customer.getCustomerId());
    }

    @Test
    @DisplayName("고객 아이디로 보유하고 있는 바우처 조회를 성공한다.")
    void testFindVouchersByCustomerIdSuccessful_ReturnList() {
        //given
        final Customer customer = new Customer(UUID.randomUUID(), "test-customer", CustomerType.NORMAL);
        final List<Voucher> vouchers = List.of(
                new Voucher(UUID.randomUUID(), new BigDecimal(1000), VoucherType.FIXED),
                new Voucher(UUID.randomUUID(), new BigDecimal(40), VoucherType.PERCENT));
        doReturn(Optional.of(customer)).when(customerRepository).findById(any(UUID.class));
        doReturn(vouchers).when(voucherRepository).findByCustomerId(any(UUID.class));

        //when
        List<VoucherResponse> voucherResponses = voucherService.findByCustomerId(customer.getCustomerId());

        //then
        assertThat(voucherResponses, hasSize(greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("고객 보유 바우처 삭제 시 존재하지 않는 고객이면 삭제를 실패한다.")
    void testReleaseVoucherFromCustomerFailed_NonExistentCustomer() {
        //given
        final VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        doReturn(false).when(customerRepository).existById(any(UUID.class));

        //when & then
        assertThatThrownBy(() -> voucherService.releaseFromCustomer(request));

        //verify
        verify(customerRepository).existById(request.customerId());
    }

    @Test
    @DisplayName("고객 보유 바우처 삭제 시 없는 바우처이면 삭제를 실패한다.")
    void testReleaseVoucherFromCustomerFailed_NonExistentVoucher() {
        //given
        final NoSuchElementException exception = new NoSuchElementException();
        final VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        doReturn(true).when(customerRepository).existById(any(UUID.class));
        doThrow(exception).when(voucherRepository).findById(any(UUID.class));

        //when & then
        assertThatThrownBy(() -> voucherService.releaseFromCustomer(request));

        //verify
        verify(voucherRepository).findById(request.voucherId());
    }

    @Test
    @DisplayName("고객 보유 바우처 삭제를 성공한다.")
    void testReleaseVoucherFromVoucherSuccessful() {
        //given
        final VoucherCustomerRequest request = new VoucherCustomerRequest(UUID.randomUUID(), UUID.randomUUID());
        final Voucher voucher = new Voucher(request.voucherId(), new BigDecimal(10000), VoucherType.FIXED, request.customerId());
        doReturn(true).when(customerRepository).existById(request.customerId());
        doReturn(Optional.of(voucher)).when(voucherRepository).findById(request.voucherId());
        doReturn(voucher).when(voucherRepository).save(any(Voucher.class));

        //when
        voucherService.releaseFromCustomer(request);

        //verify
        verify(voucherRepository).findById(request.voucherId());
        verify(voucherRepository).save(any(Voucher.class));
    }
}
