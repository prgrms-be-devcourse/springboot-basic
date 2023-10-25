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
import org.prgrms.vouchermanager.domain.customer.CustomerRequestDto;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
import org.prgrms.vouchermanager.repository.customer.JdbcCustomerRepository;
import org.prgrms.vouchermanager.repository.voucher.JdbcVoucherRepository;
import org.prgrms.vouchermanager.repository.wallet.JdbcWalletRepository;
import org.prgrms.vouchermanager.testdata.CustomerData;
import org.prgrms.vouchermanager.testdata.VoucherData;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@Slf4j
@Transactional
@ExtendWith(SpringExtension.class)
class WalletServiceTest {
//    @Mock
//    private JdbcWalletRepository walletRepository;
//    @Mock
//    private JdbcCustomerRepository customerRepository;
//    @Mock
//    private JdbcVoucherRepository voucherRepository;
//    @InjectMocks
//    private WalletService walletService;
//    @InjectMocks
//    private CustomerService customerService;
//    @InjectMocks
//    private VoucherService voucherService;
//
//    @Nested
//    @DisplayName("생성")
//    class create {
//        @Test
//        @DisplayName("지갑 정보 생성에 성공한다")
//        void createWallet() {
//            Customer customer1 = CustomerData.getCustomer();
//            Voucher voucher1 = VoucherData.getFixedVoucher();
//            when(customerRepository.save(any())).thenReturn(customer1);
//
//
//            Customer customer = customerRepository.save(customer1);
//            Voucher voucher = voucherService.createVoucher(VoucherData.getFixedVoucher());
//            WalletRequestDto dto = WalletRequestDto.builder().customerEmail(customer.getEmail()).voucher(voucher).build();
//            when(walletRepository.save(any())).thenReturn(dto);
//
//
//            WalletRequestDto result = walletService.createWallet(dto);
//
//            assertThat(result).isEqualTo(dto);
//
//        }
//    }
//
//    @Nested
//    @DisplayName("조회")
//    class read {
//        @Test
//        @DisplayName("해당 이메일과 일치하는 고객의 바우처 정보를 가져온다")
//        void findByEmail(){
//            CustomerRequestDto customerDto = new CustomerRequestDto("jun", "email", true);
//            Customer customer1 = new Customer(UUID.randomUUID(), customerDto.getName(), customerDto.getEmail(), customerDto.getIsBlack());
//            Voucher voucher1 = VoucherData.getFixedVoucher();
//
//            when(customerRepository.save(any())).thenReturn(customer1);
//            when(voucherRepository.save(any())).thenReturn(voucher1);
//
//            Customer customer = customerService.createCustomer(customerDto);
//            Voucher voucher = voucherService.createVoucher(voucher1);
//            WalletRequestDto dto = WalletRequestDto.builder().customerEmail(customer.getEmail()).voucher(voucher).build();
//            when(walletRepository.save(any())).thenReturn(dto);
//            walletService.createWallet(dto);
//
//            Optional<Wallet> wallet = walletService.findByEmail(customer.getEmail());
//
//            assertThat(wallet).isNotEmpty();
//        }
//    }
}