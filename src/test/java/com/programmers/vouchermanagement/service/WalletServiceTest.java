package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.CustomerDto;
import com.programmers.vouchermanagement.dto.VoucherDto;
import com.programmers.vouchermanagement.message.ErrorMessage;
import com.programmers.vouchermanagement.repository.wallet.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {
    @InjectMocks
    private WalletService walletService;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private VoucherService voucherService;
    private final List<Wallet> testWallets = new ArrayList<>();
    private final UUID customerId = UUID.randomUUID();
    private final UUID voucherId_1 = UUID.randomUUID();
    private final UUID voucherId_2 = UUID.randomUUID();
    private final Voucher voucher = VoucherFactory.createVoucher(new VoucherDto.CreateRequest("voucher", 1000, VoucherType.FIXED));
    private final Customer customer = new Customer(new CustomerDto.CreateRequest("user"));

    @BeforeEach
    void setUp() {
        testWallets.add(new Wallet(UUID.randomUUID(), customerId, voucherId_1));
        testWallets.add(new Wallet(UUID.randomUUID(), customerId, voucherId_2));
    }

    @Test
    void 고객ID로_고객의_바우처를_가져올_수_있다() {
        //given
        doReturn(testWallets).when(walletRepository).findByCustomerId(customerId);
        doReturn(voucher).when(voucherService).findVoucherById(voucherId_1);
        doReturn(voucher).when(voucherService).findVoucherById(voucherId_2);

        //when
        final List<Voucher> vouchers = walletService.findVoucherByCustomer(customerId);

        //then
        assertThat(vouchers).hasSameSizeAs(testWallets);
    }

    @Test
    void 바우처ID로_바우처를_소유한_고객들을_가져올_수_있다() {
        //given
        doReturn(Collections.singletonList(new Wallet(UUID.randomUUID(), customerId, voucherId_1)))
                .when(walletRepository).findByVoucherId(voucherId_1);
        doReturn(customer).when(customerService).findCustomerById(customerId);

        //when
        final List<Customer> customers = walletService.findCustomerByVoucher(voucherId_1);

        //then
        assertThat(customers).contains(customer);
        assertThat(customers.size()).isEqualTo(1);
    }

    @Test
    void 같은_고객에게_같은_바우처를_두_번_지급할_수_없다() {
        //given
        doReturn(true).when(walletRepository).existsByCustomerIdAndVoucherId(customerId, voucherId_1);

        //when&then
        assertThatThrownBy(() -> walletService.giveVoucherToCustomer(customerId, voucherId_1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.VOUCHER_ALREADY_EXISTS_MESSAGE.getMessage());
    }

    @Test
    void 존재하지_않는_지갑_데이터를_삭제할_수_없다() {
        //given
        doReturn(false).when(walletRepository).existsByCustomerIdAndVoucherId(customerId, voucherId_1);

        //when&then
        assertThatThrownBy(() -> walletService.deleteVoucherFromCustomer(customerId, voucherId_1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining(ErrorMessage.WALLET_NOT_FOUND_MESSAGE.getMessage());
    }
}
