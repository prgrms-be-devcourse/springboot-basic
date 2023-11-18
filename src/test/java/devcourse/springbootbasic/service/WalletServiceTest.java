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

import static devcourse.springbootbasic.TestDataFactory.*;
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
        Customer customer = generateNotBlacklistCustomers("Platypus");
        UUID customerId = customer.getId();

        Voucher voucher = generateAssignedVoucher(VoucherType.FIXED, 50, customerId);
        UUID voucherId = voucher.getId();

        VoucherAssignRequest request = new VoucherAssignRequest(customerId);

        when(voucherService.getVoucherById(voucherId)).thenReturn(voucher);
        when(customerService.getByCustomerId(customerId)).thenReturn(customer);
        when(voucherService.assignVoucherToCustomer(voucher, customer)).thenReturn(voucher.assignToCustomer(customerId));

        // When
        VoucherAssignResponse response = walletService.assignVoucherToCustomer(voucherId, request);

        // Then
        assertThat(response.getVoucherId()).isEqualTo(voucherId.toString());
        assertThat(response.getCustomerId()).isEqualTo(customerId.toString());
        assertThat(voucher.isAssigned()).isTrue();
    }

    @Test
    @DisplayName("고객 ID로 바우처 목록을 조회합니다.")
    void givenCustomerId_whenFindVouchersByCustomerId_thenListOfVouchersIsReturned() {
        // Given
        Customer customer = generateNotBlacklistCustomers("Platypus");
        UUID customerId = customer.getId();
        when(customerService.getByCustomerId(customerId)).thenReturn(customer);

        Voucher voucher1 = generateAssignedVoucher(VoucherType.FIXED, 50, customerId);
        Voucher voucher2 = generateAssignedVoucher(VoucherType.FIXED, 50, customerId);
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
        Customer customer = generateNotBlacklistCustomers("Platypus");
        UUID customerId = customer.getId();
        when(customerService.getByCustomerId(customerId)).thenReturn(customer);

        Voucher voucher = generateAssignedVoucher(VoucherType.FIXED, 50, customerId);
        UUID voucherId = voucher.getId();
        when(voucherService.getVoucherById(voucherId)).thenReturn(voucher);

        when(voucherService.unassignVoucherToCustomer(voucher)).thenReturn(voucher.unassignToCustomer());
        voucher.assignToCustomer(customerId); // 테스트를 위해 바우처를 고객에게 다시 할당

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
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 50);
        UUID voucherId = voucher.getId();
        when(voucherService.getVoucherById(voucherId)).thenReturn(voucher);

        // When, Then
        assertThatThrownBy(() -> walletService.unassignVoucherFromCustomer(voucherId))
                .isInstanceOf(VoucherException.class)
                .hasMessageContaining(VoucherErrorMessage.NOT_ASSIGNED.getMessage());
    }

    @Test
    @DisplayName("바우처 ID로 고객을 조회합니다.")
    void givenAssignedVoucher_whenFindCustomerByVoucherId_thenCustomerIsFound() {
        // Given
        Customer customer = generateNotBlacklistCustomers("Platypus");
        UUID customerId = customer.getId();
        when(customerService.getByCustomerId(customerId)).thenReturn(customer);

        Voucher voucher = generateAssignedVoucher(VoucherType.FIXED, 50, customerId);
        UUID voucherId = voucher.getId();
        when(voucherService.getVoucherById(voucherId)).thenReturn(voucher);

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
        Voucher voucher = generateAssignedVoucher(VoucherType.FIXED, 50, null);
        UUID voucherId = voucher.getId();
        when(voucherService.getVoucherById(voucherId)).thenReturn(voucher);

        // When, Then
        assertThatThrownBy(() -> walletService.findCustomerByVoucherId(voucherId))
                .isInstanceOf(VoucherException.class)
                .hasMessageContaining(VoucherErrorMessage.NOT_ASSIGNED.getMessage());
    }
}
