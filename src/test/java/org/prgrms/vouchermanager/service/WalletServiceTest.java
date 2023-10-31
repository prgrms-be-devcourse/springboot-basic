package org.prgrms.vouchermanager.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
import org.prgrms.vouchermanager.exception.NotExistEmailException;
import org.prgrms.vouchermanager.exception.NotExistVoucherException;
import org.prgrms.vouchermanager.repository.customer.JdbcCustomerRepository;
import org.prgrms.vouchermanager.repository.voucher.JdbcVoucherRepository;
import org.prgrms.vouchermanager.repository.wallet.JdbcWalletRepository;
import org.prgrms.vouchermanager.testdata.CustomerData;
import org.prgrms.vouchermanager.testdata.VoucherData;
import org.prgrms.vouchermanager.testdata.WalletData;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
class WalletServiceTest {
    @Mock
    private JdbcWalletRepository walletRepository;
    @Mock
    private JdbcVoucherRepository voucherRepository;
    @Mock
    private JdbcCustomerRepository customerRepository;
    @InjectMocks
    private WalletService service;

    @Nested
    @DisplayName("생성")
    class create {
        @Test
        @DisplayName("지갑 dto를 받아 저장할 수 있다")
        void createWallet() {
            WalletRequestDto wallet = WalletData.getWalletDto();
            when(customerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(CustomerData.getCustomer()));
            when(walletRepository.save(any(WalletRequestDto.class))).thenReturn(wallet);

            WalletRequestDto wallet1 = service.createWallet(wallet);

            assertThat(wallet).isEqualTo(wallet1);
            verify(walletRepository).save(any(WalletRequestDto.class));
        }
        @Test
        @DisplayName("존재하지 않는 고객을 지갑에 저장하면 예외가 발생한다.")
        void createWalletNoCustomer() {
            WalletRequestDto wallet = WalletData.getWalletDto();
            Assertions.assertThrows(NotExistEmailException.class, () -> service.createWallet(wallet));
        }
    }
    @Nested
    @DisplayName("조회")
    class find {
        @Test
        @DisplayName("고객의 이메일로 해당 이메일 고객의 지갑 정보를 조회할 수 있다.")
        void findByEmail() {
            Wallet wallet = new Wallet(1, "example@naver.com", UUID.randomUUID());
            Customer customer = CustomerData.getCustomer();
            when(walletRepository.findByEmail(any(String.class))).thenReturn(Optional.of(wallet));

            Optional<Wallet> wallet1 = service.findByEmail("example@naver.com");

            assertThat(wallet1).contains(wallet);
            verify(walletRepository).findByEmail(any(String.class));
        }
        @Test
        @DisplayName("존재하지 않는 고객의 지갑 정보 조회 시 예외가 발생한다")
        void findByEmailNotExist() {
            when(walletRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

            Assertions.assertThrows(NotExistEmailException.class, () -> service.findByEmail("example@naver.com"));
            verify(walletRepository).findByEmail(any(String.class));
        }
        @Test
        @DisplayName("특정 바우처를 보유한 고객의 지갑정보를 조회할 수 있다")
        void findByVoucher() {
            Voucher voucher = VoucherData.getFixedVoucher();
            Wallet wallet = WalletData.getWallet();
            when(voucherRepository.findByID(any(UUID.class))).thenReturn(Optional.of(voucher));
            when(walletRepository.findByVoucher(any(Voucher.class))).thenReturn(Optional.of(wallet));

            Optional<Wallet> result = service.findByVoucher(voucher);

            assertThat(result).contains(wallet);
            verify(walletRepository).findByVoucher(any(Voucher.class));
        }
        @Test
        @DisplayName("없는 바우처에 대해 지갑 조회 시 예외가 발생한다")
        void findByVoucherNotExist() {
            when(voucherRepository.findByID(any(UUID.class))).thenReturn(Optional.empty());
            when(walletRepository.findByVoucher(any(Voucher.class))).thenReturn(Optional.of(WalletData.getWallet()));

            Assertions.assertThrows(NotExistVoucherException.class, () -> service.findByVoucher(VoucherData.getFixedVoucher()));
            verify(voucherRepository).findByID(any(UUID.class));
        }
    }
    @Nested
    @DisplayName("삭제")
    class delete {
        @Test
        @DisplayName("이메일로 해당 고객 정보를 삭제할 수 있다")
        void deleteByEmail() {
            Customer customer = CustomerData.getCustomer();
            Wallet wallet = WalletData.getWallet();
            when(customerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(customer));
            when(walletRepository.deleteByEmail(any(String.class))).thenReturn(Optional.of(wallet));

            Optional<Wallet> result = service.deleteByEmail("123@naver.com");

            assertThat(result).contains(wallet);
            verify(customerRepository).findByEmail(any(String.class));
            verify(walletRepository).deleteByEmail(any(String.class));
        }
        @Test
        @DisplayName("존재하지 않는 고객을 삭제하려하면 예외가 발생한다")
        void deleteByEmailNotExist(){
            when(customerRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
            when(walletRepository.deleteByEmail(any(String.class))).thenReturn(Optional.empty());

            Assertions.assertThrows(NotExistEmailException.class, ()->service.deleteByEmail("example@naver.om"));
        }
    }
}