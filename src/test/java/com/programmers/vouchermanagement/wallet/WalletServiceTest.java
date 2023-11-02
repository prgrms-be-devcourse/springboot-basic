package com.programmers.vouchermanagement.wallet;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.customer.exception.CustomerNotFoundException;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotFoundException;
import com.programmers.vouchermanagement.voucher.mapper.VoucherPolicyMapper;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import com.programmers.vouchermanagement.wallet.application.WalletService;
import com.programmers.vouchermanagement.wallet.domain.Wallet;
import com.programmers.vouchermanagement.wallet.dto.WalletRequestDto;
import com.programmers.vouchermanagement.wallet.repository.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @InjectMocks
    WalletService walletService;

    @Mock
    WalletRepository walletRepository;

    @Mock
    VoucherRepository voucherRepository;

    @Mock
    CustomerRepository customerRepository;

    @Test
    @DisplayName("존재하지 않는 Customer 아이디는 Wallet 생성 시 예외를 발생시킨다.")
    void testCustomerNotFoundException() {

        // given
        UUID customerId = UUID.randomUUID();
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(10000L, VoucherType.FIXED));
        when(customerRepository.findById(customerId)).thenThrow(new CustomerNotFoundException());
//        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        // when - then
        assertThatThrownBy(() -> walletService.createWallet(new WalletRequestDto(customerId, voucher.getVoucherId())))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 Voucher 아이디는 Wallet 생성 시 예외를 발생시킨다.")
    void testVoucherNotFoundException() {

        // given
        Customer customer = new Customer(UUID.randomUUID(), "김뫄뫄", CustomerType.BLACK);
        UUID voucherId = UUID.randomUUID();
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(voucherRepository.findById(voucherId)).thenThrow(new VoucherNotFoundException());

        // when - then
        assertThatThrownBy(() -> walletService.createWallet(new WalletRequestDto(customer.getCustomerId(), voucherId)))
                .isInstanceOf(VoucherNotFoundException.class);
    }

    @Test
    @DisplayName("Customer 아이디로 고객이 보유한 Voucher 목록을 조회할 수 있다.")
    void successReadVouchersByCustomer() {

        // given
        Customer customer = new Customer(UUID.randomUUID(), "김뫄뫄", CustomerType.BLACK);
        Voucher voucher1 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(10000L, VoucherType.FIXED));
        Voucher voucher2 = new Voucher(UUID.randomUUID(), VoucherType.PERCENT, VoucherPolicyMapper.toEntity(80L, VoucherType.PERCENT));
        Voucher voucher3 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(500L, VoucherType.FIXED));
        List<Wallet> wallets = new ArrayList<>() {{
            add(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher1.getVoucherId()));
            add(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher2.getVoucherId()));
            add(new Wallet(UUID.randomUUID(), customer.getCustomerId(), voucher3.getVoucherId()));
        }};
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(walletRepository.findByCustomerId(customer.getCustomerId())).thenReturn(wallets);
        when(voucherRepository.findById(voucher1.getVoucherId())).thenReturn(Optional.of(voucher1));
        when(voucherRepository.findById(voucher2.getVoucherId())).thenReturn(Optional.of(voucher2));
        when(voucherRepository.findById(voucher3.getVoucherId())).thenReturn(Optional.of(voucher3));

        // when
        List<VoucherResponseDto> voucherResponseDtos = walletService.readVouchersByCustomer(customer.getCustomerId());

        // then
        assertThat(voucherResponseDtos).hasSize(3)
                .extracting(VoucherResponseDto::getVoucherId)
                .contains(voucher1.getVoucherId(), voucher2.getVoucherId(), voucher3.getVoucherId());
    }

    @Test
    @DisplayName("Voucher 아이디로 특정 Voucher를 보유한 Customer 목록을 조회할 수 있다.")
    void successReadCustomersByVoucher() {

        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(10000L, VoucherType.FIXED));
        Customer customer1 = new Customer(UUID.randomUUID(), "김뫄뫄", CustomerType.BLACK);
        Customer customer2 = new Customer(UUID.randomUUID(), "이롸롸", CustomerType.BLACK);
        Customer customer3 = new Customer(UUID.randomUUID(), "최뭐뭐", CustomerType.BLACK);
        List<Wallet> wallets = new ArrayList<>() {{
            add(new Wallet(UUID.randomUUID(), customer1.getCustomerId(), voucher.getVoucherId()));
            add(new Wallet(UUID.randomUUID(), customer2.getCustomerId(), voucher.getVoucherId()));
            add(new Wallet(UUID.randomUUID(), customer3.getCustomerId(), voucher.getVoucherId()));
        }};
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.of(customer1));
        when(customerRepository.findById(customer2.getCustomerId())).thenReturn(Optional.of(customer2));
        when(customerRepository.findById(customer3.getCustomerId())).thenReturn(Optional.of(customer3));
        when(walletRepository.findByVoucherId(voucher.getVoucherId())).thenReturn(wallets);
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        // when
        List<CustomerResponseDto> customerResponseDtos = walletService.readCustomersByVoucher(voucher.getVoucherId());

        // then
        assertThat(customerResponseDtos).hasSize(3)
                .extracting(CustomerResponseDto::getCustomerId)
                .contains(customer1.getCustomerId(), customer2.getCustomerId(), customer3.getCustomerId());
    }
}
