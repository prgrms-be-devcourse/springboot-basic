package com.programmers.vouchermanagement.wallet.service;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import com.programmers.vouchermanagement.wallet.repository.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class WalletServiceTest {
    @InjectMocks
    WalletService walletService;
    @Mock
    WalletRepository walletRepository;
    @Mock
    UUID mockUUID;
    @Mock
    Customer mockCustomer;
    @Mock
    Voucher mockVoucher;
    @Mock
    Ownership ownership;

    @Test
    @DisplayName("🆗 단위 테스트를 위해 Service에 Mock 객체(Repository) 주입")
    void initTest() {
        assertThat(walletRepository).isNotNull();
        assertThat(walletService).isNotNull();
    }

    @Test
    @DisplayName("🆗 바우처를 고객에게 할당할 수 있다.")
    void allocate() {
        walletService.allocate(ownership);

        verify(walletRepository, times(1)).save(ownership);
    }

    @Test
    @DisplayName("🆗 고객 id로 고객이 가진 바우처를 조회할 수 있다.")
    void findAllVoucherByCustomerId() {
        List<Voucher> mockVoucherList = mock(List.class);
        when(walletRepository.findAllVoucherByCustomerId(mockUUID)).thenReturn(mockVoucherList);

        assertThat(walletService.readAllVoucherByCustomerId(mockUUID)).isEqualTo(mockVoucherList);

        verify(walletRepository, times(1)).findAllVoucherByCustomerId(mockUUID);
    }

    @Test
    @DisplayName("🆗 고객에게 할당한 바우처를 제거할 수 있다.")
    void deleteVoucherFromCustomer() {
        walletService.deleteVoucherFromCustomer(mockUUID);

        verify(walletRepository, times(1)).delete(mockUUID);
    }

    @Test
    @DisplayName("🆗 바우처 id로 바우처가 할당된 고객을 조회할 수 있다.")
    void findCustomerByVoucherId() {
        Optional<Customer> customerOptional = Optional.of(mockCustomer);
        when(walletRepository.findCustomerByVoucherId(mockUUID)).thenReturn(customerOptional);

        assertThat(walletService.readCustomerByVoucherId(mockUUID)).isEqualTo(customerOptional.get());
        verify(walletRepository, times(1)).findCustomerByVoucherId(mockUUID);
    }

    @Test
    @DisplayName("🚨 바우처가 없으면, 바우처가 할당된 고객을 조회할 수 없다.")
    void findCustomerByNonExistVoucherId() {
        when(walletRepository.findCustomerByVoucherId(mockUUID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> walletService.readCustomerByVoucherId(mockUUID));
        verify(walletRepository, times(1)).findCustomerByVoucherId(mockUUID);
    }
}