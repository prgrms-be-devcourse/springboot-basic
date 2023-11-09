package com.programmers.springbootbasic.domain.wallet.service;

import com.programmers.springbootbasic.domain.customer.entity.Customer;
import com.programmers.springbootbasic.domain.customer.repository.CustomerRepository;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.service.VoucherType;
import com.programmers.springbootbasic.domain.wallet.dto.WalletServiceRequestDto;
import com.programmers.springbootbasic.domain.wallet.entity.Wallet;
import com.programmers.springbootbasic.domain.wallet.repository.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.springbootbasic.domain.customer.exception.ErrorMsg.CUSTOMER_NOT_FOUND;
import static com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg.VOUCHER_NOT_FOUND;
import static com.programmers.springbootbasic.domain.wallet.exception.ErrorMsg.WALLET_ALREADY_EXIST;
import static com.programmers.springbootbasic.domain.wallet.exception.ErrorMsg.WALLET_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Wallet Service Test")
class WalletServiceTest {
    private static final UUID CUSTOMER_ID = UUID.randomUUID();
    private static final String EMAIL = "test@gmail.com";
    private static final String NAME = "test";
    private static final int VOUCHER_TYPE = 1;
    private static final UUID VOUCHER_ID = UUID.randomUUID();
    private static final long VALUE = 20L;
    private static final LocalDate VOUCHER_CREATED_AT = LocalDate.now();
    private static final Customer CUSTOMER = Customer.builder()
            .customerId(CUSTOMER_ID)
            .email(EMAIL)
            .name(NAME)
            .isBlacklist(false)
            .build();
    private static final Voucher VOUCHER = VoucherType.of(VOUCHER_TYPE, VOUCHER_ID, VALUE, VOUCHER_CREATED_AT);
    @InjectMocks
    private WalletService walletService;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private VoucherRepository voucherRepository;
    @Mock
    private CustomerRepository customerRepository;

    @Test
    void testCreateWalletSuccess() {
        // Arrange
        Wallet expectedResult = Wallet.builder()
                .email(EMAIL)
                .voucherId(VOUCHER_ID)
                .build();
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(CUSTOMER));
        when(voucherRepository.findById(VOUCHER_ID)).thenReturn(Optional.of(VOUCHER));
        when(walletRepository.findByValues(any(String.class), any(UUID.class))).thenReturn(Optional.empty());
        when(walletRepository.save(any(Wallet.class))).thenReturn(expectedResult);
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .email(EMAIL)
                .voucherId(VOUCHER_ID)
                .build();
        // Act
        Wallet actualResult = walletService.createWallet(walletServiceRequestDto);
        // Assert
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @DisplayName("Test createWallet Fail: When customer Not Found")
    @Test
    void testCreateWalletFailWhenCustomerNotFound() {
        // Arrange
        String expectedEmail = "test2@gmail.com";
        when(customerRepository.findByEmail(expectedEmail)).thenReturn(Optional.empty());
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .email(expectedEmail)
                .voucherId(VOUCHER_ID)
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> walletService.createWallet(walletServiceRequestDto));
        assertThat(actualResult.getMessage()).isEqualTo(CUSTOMER_NOT_FOUND.getMessage());
    }

    @DisplayName("Test createWallet Fail: When Voucher Not Found")
    @Test
    void testCreateWalletFailWhenVoucherNotFound() {
        // Arrange
        UUID expectedVoucherId = UUID.randomUUID();
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(CUSTOMER));
        when(voucherRepository.findById(expectedVoucherId)).thenReturn(Optional.empty());
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .email(EMAIL)
                .voucherId(expectedVoucherId)
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> walletService.createWallet(walletServiceRequestDto));
        assertThat(actualResult.getMessage()).isEqualTo(VOUCHER_NOT_FOUND.getMessage());
    }

    @DisplayName("Test createWallet Fail: When Wallet Already Exist")
    @Test
    void testCreateWalletFailWhenWalletAlreadyExist() {
        // Arrange
        Wallet expectedWallet = Wallet.builder()
                .email(EMAIL)
                .voucherId(VOUCHER_ID)
                .build();
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(CUSTOMER));
        when(voucherRepository.findById(VOUCHER_ID)).thenReturn(Optional.of(VOUCHER));
        when(walletRepository.findByValues(any(String.class), any(UUID.class))).thenReturn(Optional.of(expectedWallet));
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .email(EMAIL)
                .voucherId(VOUCHER_ID)
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> walletService.createWallet(walletServiceRequestDto));
        assertThat(actualResult.getMessage()).isEqualTo(WALLET_ALREADY_EXIST.getMessage());
    }

    @Test
    void testFindWalletsByCustomerEmailSuccess() {
        // Arrange
        Wallet expectedWallet = Wallet.builder()
                .email(EMAIL)
                .voucherId(VOUCHER_ID)
                .build();
        List<Wallet> expectedResult = List.of(expectedWallet);
        when(walletRepository.findByCustomerEmail(EMAIL)).thenReturn(expectedResult);
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(CUSTOMER));
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .email(EMAIL)
                .build();
        // Act
        List<Wallet> actualResult = walletService.findWalletsByCustomerEmail(walletServiceRequestDto);
        // Assert
        assertThat(actualResult.size()).isEqualTo(expectedResult.size());
        assertThat(actualResult).contains(expectedWallet);
    }

    @DisplayName("Test findWalletsByCustomerEmail Fail: When Customer Not Found")
    @Test
    void testFindWalletsByCustomerEmailFailWhenCustomerNotFound() {
        // Arrange
        String expectedEmail = "test2@gmail.com";
        when(customerRepository.findByEmail(expectedEmail)).thenReturn(Optional.empty());
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .email(expectedEmail)
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> walletService.findWalletsByCustomerEmail(walletServiceRequestDto));
        assertThat(actualResult.getMessage()).isEqualTo(CUSTOMER_NOT_FOUND.getMessage());
    }

    @Test
    void testFindWalletsByVoucherIdSuccess() {
        // Arrange
        Wallet expectedWallet = Wallet.builder()
                .email(EMAIL)
                .voucherId(VOUCHER_ID)
                .build();
        List<Wallet> expectedResult = List.of(expectedWallet);
        when(walletRepository.findByVoucherId(VOUCHER_ID)).thenReturn(expectedResult);
        when(voucherRepository.findById(VOUCHER_ID)).thenReturn(Optional.of(VOUCHER));
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .voucherId(VOUCHER_ID)
                .build();
        // Act
        List<Wallet> actualResult = walletService.findWalletsByVoucherId(walletServiceRequestDto);
        // Assert
        assertThat(actualResult.size()).isEqualTo(expectedResult.size());
        assertThat(actualResult).contains(expectedWallet);
    }

    @DisplayName("Test findWalletsByVoucherId Fail: When Voucher Not Found")
    @Test
    void testFindWalletsByVoucherIdFailWhenVoucherNotFound() {
        UUID expectedVoucherId = UUID.randomUUID();
        when(voucherRepository.findById(expectedVoucherId)).thenReturn(Optional.empty());
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .voucherId(expectedVoucherId)
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> walletService.findWalletsByVoucherId(walletServiceRequestDto));
        assertThat(actualResult.getMessage()).isEqualTo(VOUCHER_NOT_FOUND.getMessage());
    }

    @Test
    void testDeleteWalletSuccess() {
        // Arrange
        Wallet expectedWallet = Wallet.builder()
                .email(EMAIL)
                .voucherId(VOUCHER_ID)
                .build();
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(CUSTOMER));
        when(voucherRepository.findById(VOUCHER_ID)).thenReturn(Optional.of(VOUCHER));
        when(walletRepository.findByValues(any(String.class), any(UUID.class))).thenReturn(Optional.of(expectedWallet));
        doNothing().when(walletRepository).delete(expectedWallet);
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .email(EMAIL)
                .voucherId(VOUCHER_ID)
                .build();
        // Act
        walletService.deleteWallet(walletServiceRequestDto);
    }

    @DisplayName("Test deleteWallet Fail: When customer Not Found")
    @Test
    void testDeleteWalletFailWhenCustomerNotFound() {
        // Arrange
        String expectedEmail = "test2@gmail.com";
        when(customerRepository.findByEmail(expectedEmail)).thenReturn(Optional.empty());
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .email(expectedEmail)
                .voucherId(VOUCHER_ID)
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> walletService.deleteWallet(walletServiceRequestDto));
        assertThat(actualResult.getMessage()).isEqualTo(CUSTOMER_NOT_FOUND.getMessage());
    }

    @DisplayName("Test deleteWallet Fail: When Voucher Not Found")
    @Test
    void testDeleteWalletFailWhenVoucherNotFound() {
        // Arrange
        UUID expectedVoucherId = UUID.randomUUID();
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(CUSTOMER));
        when(voucherRepository.findById(expectedVoucherId)).thenReturn(Optional.empty());
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .email(EMAIL)
                .voucherId(expectedVoucherId)
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> walletService.deleteWallet(walletServiceRequestDto));
        assertThat(actualResult.getMessage()).isEqualTo(VOUCHER_NOT_FOUND.getMessage());
    }

    @DisplayName("Test deleteWallet Fail: When Wallet Not Found")
    @Test
    void testDeleteWalletFailWhenWalletAlreadyExist() {
        // Arrange
        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.of(CUSTOMER));
        when(voucherRepository.findById(VOUCHER_ID)).thenReturn(Optional.of(VOUCHER));
        when(walletRepository.findByValues(any(String.class), any(UUID.class))).thenReturn(Optional.empty());
        WalletServiceRequestDto walletServiceRequestDto = WalletServiceRequestDto.builder()
                .email(EMAIL)
                .voucherId(VOUCHER_ID)
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(RuntimeException.class, () -> walletService.deleteWallet(walletServiceRequestDto));
        assertThat(actualResult.getMessage()).isEqualTo(WALLET_NOT_FOUND.getMessage());
    }
}