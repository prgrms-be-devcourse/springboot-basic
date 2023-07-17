package com.prgrms.service.wallet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prgrms.dto.customer.CustomerConverter;
import com.prgrms.dto.customer.CustomerResponse;
import com.prgrms.dto.voucher.VoucherConverter;
import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.dto.wallet.WalletConverter;
import com.prgrms.dto.wallet.WalletRequest;
import com.prgrms.model.customer.Customer;
import com.prgrms.model.customer.Name;
import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.wallet.Wallet;
import com.prgrms.repository.wallet.WalletRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class WalletServiceTest {

    private static final int CUSTOMER_ID = 10;
    private static final int VOUCHER_ID = 10;
    private static final int WALLET_ID = 10;
    private static final Name TEST_USER_NAME = new Name("test-user");
    private static final String TEST_USER_EMAIL = "test1-user@gmail.com";
    private static final LocalDateTime CREATE_AT = LocalDateTime.now();

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private VoucherConverter voucherConverter;

    @Mock
    private WalletConverter walletConverter;

    @Mock
    private CustomerConverter customerConverter;

    @InjectMocks
    private WalletService walletService;

    private WalletRequest walletRequest;
    private Wallet wallet;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        walletRequest = new WalletRequest(CUSTOMER_ID, VOUCHER_ID);
        wallet = new Wallet(WALLET_ID, CUSTOMER_ID, VOUCHER_ID);
    }

    @Test
    @DisplayName("사용자가 바우처를 등록하는 지갑 정보와 지갑 정보를 등록해주는 서비스로 등록된 지갑정보는 같다.")
    void giveVoucher_InsertWallet_Equals() {
        //given
        when(walletConverter.convertWallet(walletRequest)).thenReturn(wallet);
        when(walletRepository.insert(wallet)).thenReturn(wallet);

        //when
        Wallet result = walletService.giveVoucher(walletRequest);

        //then
        assertThat(result).isEqualTo(wallet);
        verify(walletRepository, times(1)).insert(wallet);
    }

    @Test
    @DisplayName("사용자가 삭제하고자 하는 지갑 정보와 바우처를 삭제헤주는 서비스를 통해 삭제된 지갑 정보는 같다.")
    void takeVoucher_DeleteWallet_Equals() {
        //given
        when(walletRepository.deleteWithVoucherIdAndCustomerId(VOUCHER_ID, CUSTOMER_ID)).thenReturn(
                wallet);

        //when
        Wallet result = walletService.takeVoucher(walletRequest);

        //then
        assertThat(result).isEqualTo(result);
        verify(walletRepository, times(1)).deleteWithVoucherIdAndCustomerId(VOUCHER_ID,
                CUSTOMER_ID);
    }

    @Test
    @DisplayName("임의로 고객의 정보를 넣었을 때, 그 고객의 정보가 바우처 아이디를 통해 찾은 고객 정보 리스트에 포함되어 있다.")
    void customerList_CustomerResponseList_Contains() {
        //given
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer(CUSTOMER_ID, TEST_USER_NAME, TEST_USER_EMAIL, CREATE_AT);
        customers.add(customer);
        List<CustomerResponse> customerResponses = List.of(new CustomerResponse(customer));

        when(walletRepository.findAllCustomersByVoucher(VOUCHER_ID)).thenReturn(customers);
        when(customerConverter.convertCustomerResponse(customers)).thenReturn(customerResponses);

        //when
        List<CustomerResponse> result = walletService.customerList(VOUCHER_ID);

        //then
        assertThat(result).containsExactlyElementsOf(customerResponses);
        verify(walletRepository, times(1)).findAllCustomersByVoucher(VOUCHER_ID);
        verify(customerConverter, times(1)).convertCustomerResponse(customers);
    }

    @Test
    @DisplayName("임의로 바우처의 정보를 넣었을 때, 그 바우처의 정보가 고객 아이디를 통해 찾은 바우처 정보 리스트에 포함되어 있다.")
    void voucherList_VoucherResponseList_Contains() {
        //given
        Voucher voucher = new FixedAmountVoucher(VOUCHER_ID, new FixedDiscount(20),
                VoucherType.FIXED_AMOUNT_VOUCHER);
        List<Voucher> voucherList = List.of(voucher);
        Vouchers vouchers = new Vouchers(voucherList);
        List<VoucherResponse> voucherResponses = List.of(new VoucherResponse(voucher));

        when(walletRepository.findAllVouchersByCustomer(CUSTOMER_ID)).thenReturn(vouchers);
        when(voucherConverter.convertVoucherResponse(vouchers)).thenReturn(voucherResponses);

        //when
        List<VoucherResponse> result = walletService.voucherList(CUSTOMER_ID);

        //then
        assertThat(result).containsExactlyElementsOf(voucherResponses);
        verify(walletRepository, times(1)).findAllVouchersByCustomer(CUSTOMER_ID);
        verify(voucherConverter, times(1)).convertVoucherResponse(vouchers);
    }

}
