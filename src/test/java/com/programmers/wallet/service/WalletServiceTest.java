package com.programmers.wallet.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import com.programmers.customer.domain.Customer;
import com.programmers.customer.dto.CustomerResponseDto;
import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VouchersResponseDto;
import com.programmers.wallet.repository.JdbcWalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class WalletServiceTest {

    @Mock
    private JdbcWalletRepository jdbcWalletRepository;

    private WalletService walletService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        walletService = new WalletService(jdbcWalletRepository);
    }

    @DisplayName("고객이 보유하고 있는 바우처를 조회한다")
    @Test
    public void findVouchersByCustomerId() {
        //given
        UUID customerId = UUID.randomUUID();

        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), "voucher1", 10L));
        vouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), "voucher2", 20L));

        when(jdbcWalletRepository.findVouchersByCustomerId(customerId)).thenReturn(vouchers);

        //when
        VouchersResponseDto vouchersResponseDto = walletService.findVouchersByCustomerId(customerId);

        //then
        assertThat(vouchers.size(), is(vouchersResponseDto.vouchers().size()));
    }

    @DisplayName("특정 바우처를 보유한 고객을 조회한다")
    @Test
    public void findCustomerByVoucherId() {
        //given
        UUID voucherId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        Customer customer = new Customer(customerId, "customerName");
        when(jdbcWalletRepository.findCustomerByVoucherId(voucherId)).thenReturn(Optional.of(customer));

        //when
        CustomerResponseDto customerResponseDto = walletService.findCustomerByVoucherId(voucherId);

        //then
        assertThat(customerId, is(customerResponseDto.id()));
        assertThat("customerName", is(customerResponseDto.name()));
    }
}