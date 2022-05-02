package org.prgms.kdtspringvoucher.wallet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;
import org.prgms.kdtspringvoucher.customer.repository.CustomerRepository;
import org.prgms.kdtspringvoucher.voucher.domain.FixedAmountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.prgms.kdtspringvoucher.voucher.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    VoucherRepository voucherRepository;
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    WalletService walletService;

    Customer customer = new Customer(UUID.randomUUID(), "test", "test@email.com", CustomerType.BASIC, LocalDateTime.now());
    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), customer.getCustomerId(),100L, VoucherType.FIXED, LocalDateTime.now());

    @Test
    @DisplayName("고객에게 voucher를 할당하고 반환받아야한다.")
    void assignVoucherToCustomerTest() {
        when(voucherRepository.update(any())).thenReturn(voucher);

        Voucher assignedVoucher = walletService.assignVoucherToCustomer(customer, voucher);

        assertThat(assignedVoucher,is(voucher));
        verify(voucherRepository).update(any());
    }

    @Test
    @DisplayName("특정 고객에게 할당된 voucher들을 출력하고 반환해야한다.")
    void showVouchersAssignedToCustomerTest() {
        when(voucherRepository.findByCustomerId(customer.getCustomerId())).thenReturn(List.of(voucher));

        walletService.showVouchersAssignedToCustomer(customer);
    }

    @Test
    @DisplayName("특정 Voucher를 가지고 고객을 조회할 수 있어야한다.")
    void showCustomerByVoucherIdTest() {
        when(customerRepository.findById(voucher.getCustomerId())).thenReturn(Optional.of(customer));

        walletService.showCustomerByVoucherId(voucher);
    }
}