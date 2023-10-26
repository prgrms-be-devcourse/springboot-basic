package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.dto.wallet.VoucherAssignRequest;
import devcourse.springbootbasic.dto.wallet.VoucherAssignResponse;
import devcourse.springbootbasic.exception.VoucherErrorMessage;
import devcourse.springbootbasic.exception.VoucherException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;
    @Mock
    private CustomerService customerService;
    @Mock
    private VoucherService voucherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("주어진 바우처와 고객이 존재할 때 바우처를 고객에게 할당합니다.")
    void givenVoucherAndCustomerExist_whenAssignVoucherToCustomer_thenVoucherIsAssigned() {
        // Given
        UUID voucherId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        VoucherAssignRequest request = new VoucherAssignRequest(voucherId, customerId);

        Voucher voucher = createVoucher(voucherId, customerId);
        Customer customer = createCustomer(customerId);

        when(voucherService.findById(voucherId)).thenReturn(voucher);
        when(customerService.findById(customerId)).thenReturn(customer);
        when(voucherService.assignVoucherToCustomer(voucher, customer)).thenReturn(voucher.assignToCustomer(customerId));

        // When
        VoucherAssignResponse response = walletService.assignVoucherToCustomer(request);

        // Then
        assertThat(response.getVoucherId()).isEqualTo(voucherId.toString());
        assertThat(response.getCustomerId()).isEqualTo(customerId.toString());
        assertThat(voucher.isAssigned()).isTrue();
    }

    @Test
    @DisplayName("고객 ID로 바우처 목록을 조회합니다.")
    void givenCustomerId_whenFindVouchersByCustomerId_thenListOfVouchersIsReturned() {
        // Given
        UUID customerId = UUID.randomUUID();
        Customer customer = createCustomer(customerId);
        when(customerService.findById(customerId)).thenReturn(customer);

        Voucher voucher1 = createVoucher(UUID.randomUUID(), customerId);
        Voucher voucher2 = createVoucher(UUID.randomUUID(), customerId);
        when(voucherService.findVouchersByCustomer(customer)).thenReturn(List.of(new VoucherFindResponse(voucher1), new VoucherFindResponse(voucher2)));

        // When
        List<VoucherFindResponse> vouchers = walletService.findVouchersByCustomerId(customerId);

        // Then
        assertThat(vouchers).hasSize(2);
    }

    @Test
    @DisplayName("할당된 바우처를 고객으로부터 해제합니다.")
    void givenAssignedVoucher_whenUnassignVoucherFromCustomer_thenVoucherIsUnassigned() {
        // Given
        UUID customerId = UUID.randomUUID();
        Customer customer = createCustomer(customerId);
        when(customerService.findById(customerId)).thenReturn(customer);

        UUID voucherId = UUID.randomUUID();
        Voucher voucher = createVoucher(voucherId, customerId);
        when(voucherService.findById(voucherId)).thenReturn(voucher);

        when(voucherService.unassignVoucherToCustomer(voucher)).thenReturn(createVoucher(voucherId, null));

        // When
        VoucherAssignResponse response = walletService.unassignVoucherFromCustomer(voucherId);

        // Then
        assertThat(response.getVoucherId()).isEqualTo(voucherId.toString());
        assertThat(response.getCustomerId()).isEqualTo(customerId.toString());
        verify(voucherService, times(1)).unassignVoucherToCustomer(voucher);
    }

    @Test
    @DisplayName("할당되지 않은 바우처를 고객으로부터 해제하려고 하면 예외를 발생시킵니다.")
    void givenUnassignedVoucher_whenUnassignVoucherFromCustomer_thenThrowException() {
        // Given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = createVoucher(voucherId, null);
        when(voucherService.findById(voucherId)).thenReturn(voucher);

        // When, Then
        assertThatThrownBy(() -> walletService.unassignVoucherFromCustomer(voucherId))
                .isInstanceOf(VoucherException.class)
                .hasMessageContaining(VoucherErrorMessage.NOT_ASSIGNED.getMessage());
    }

    @Test
    @DisplayName("바우처 ID로 고객을 조회합니다.")
    void givenAssignedVoucher_whenFindCustomerByVoucherId_thenCustomerIsFound() {
        // Given
        UUID voucherId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Voucher voucher = createVoucher(voucherId, customerId);
        when(voucherService.findById(voucherId)).thenReturn(voucher);

        Customer customer = createCustomer(customerId);
        when(customerService.findById(customerId)).thenReturn(customer);

        // When
        CustomerFindResponse response = walletService.findCustomerByVoucherId(voucherId);

        // Then
        assertThat(response.getId()).isEqualTo(customerId.toString());
        assertThat(response.getName()).isEqualTo(customer.getName());
        assertThat(response.getIsBlacklisted()).isEqualTo(customer.isBlacklisted() ? "Yes" : "No");
    }

    @Test
    @DisplayName("바우처 ID로 고객을 조회할 때 할당되지 않은 바우처이면 예외를 발생시킵니다.")
    void givenUnassignedVoucher_whenFindCustomerByVoucherId_thenThrowException() {
        // Given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = createVoucher(voucherId, null);
        when(voucherService.findById(voucherId)).thenReturn(voucher);

        // When, Then
        assertThatThrownBy(() -> walletService.findCustomerByVoucherId(voucherId))
                .isInstanceOf(VoucherException.class)
                .hasMessageContaining(VoucherErrorMessage.NOT_ASSIGNED.getMessage());
    }

    private Voucher createVoucher(UUID id, UUID customerId) {
        return Voucher.builder()
                .id(id)
                .voucherType(VoucherType.FIXED)
                .discountValue(50L)
                .customerId(customerId)
                .build();
    }

    private Customer createCustomer(UUID id) {
        return Customer.builder()
                .id(id)
                .name("Platypus")
                .isBlacklisted(false)
                .build();
    }
}
