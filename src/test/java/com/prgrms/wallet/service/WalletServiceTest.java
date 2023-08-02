package com.prgrms.wallet.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.prgrms.custoemer.dto.CustomerResponse;
import com.prgrms.voucher.service.dto.VoucherServiceResponse;
import com.prgrms.custoemer.model.Customer;
import com.prgrms.custoemer.model.Name;
import com.prgrms.voucher.model.voucher.FixedAmountVoucher;
import com.prgrms.voucher.model.voucher.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.FixedDiscount;
import com.prgrms.wallet.model.Wallet;
import com.prgrms.custoemer.repository.CustomerRepository;
import com.prgrms.voucher.repository.VoucherRepository;
import com.prgrms.wallet.repository.WalletRepository;
import com.prgrms.wallet.service.dto.WalletServiceRequest;
import com.prgrms.wallet.service.dto.WalletServiceResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class WalletServiceTest {

    private static final String CUSTOMER_ID = "10";
    private static final String VOUCHER_ID = "10";
    private static final String WALLET_ID = "10";
    private static final LocalDateTime CREATE_AT = LocalDateTime.now();
    private static final Name TEST_USER_NAME = new Name("test-user");
    private static final String TEST_USER_EMAIL = "test1-user@gmail.com";

    @Autowired
    private WalletService walletService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    private Wallet wallet;
    private Customer customer;
    private Voucher voucher;
    private WalletServiceRequest walletServiceRequest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        walletServiceRequest = new WalletServiceRequest(CUSTOMER_ID, VOUCHER_ID);
        customer = new Customer(CUSTOMER_ID, TEST_USER_NAME, TEST_USER_EMAIL, CREATE_AT);
        voucher = new FixedAmountVoucher(VOUCHER_ID, new FixedDiscount(20),
                VoucherType.FIXED_AMOUNT_VOUCHER, LocalDateTime.now());
        wallet = new Wallet(WALLET_ID, CUSTOMER_ID, VOUCHER_ID);
    }

    @Test
    @DisplayName("사용자가 바우처를 등록하는 지갑 정보와 지갑 정보를 등록해주는 서비스로 등록된 지갑정보는 같다.")
    void giveVoucher_InsertWallet_Equals() {
        //when
        WalletServiceResponse result = walletService.giveVoucher(WALLET_ID, walletServiceRequest);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(new WalletServiceResponse(wallet));
    }

    @Test
    @DisplayName("사용자가 삭제하고자 하는 지갑 정보와 바우처를 삭제헤주는 서비스를 통해 삭제된 지갑 정보는 같다.")
    void takeVoucher_DeleteWallet_Equals() {
        //given
        walletRepository.insert(wallet);

        //when
        WalletServiceResponse result = walletService.takeVoucher(walletServiceRequest);

        //then
        assertThat(result.isDeleted()).isEqualTo(true);

    }

    @Test
    @DisplayName("임의로 고객의 정보를 넣었을 때, 그 고객의 정보가 바우처 아이디를 통해 찾은 고객 정보 리스트에 포함되어 있다.")
    void customerList_CustomerResponseList_Contains() {
        //given
        CustomerResponse customerResponse = new CustomerResponse(customer);
        walletRepository.insert(wallet);
        customerRepository.insert(customer);
        voucherRepository.insert(voucher);

        //when
        List<CustomerResponse> result = walletService.customerList(VOUCHER_ID);

        //then
        assertThat(result.get(0).name()).isEqualTo(customerResponse.name());
        assertThat(result.get(0).email()).isEqualTo(customerResponse.email());

    }

    @Test
    @DisplayName("임의로 바우처의 정보를 넣었을 때, 그 바우처의 정보가 고객 아이디를 통해 찾은 바우처 정보 리스트에 포함되어 있다.")
    void voucherList_VoucherResponseList_Contains() {
        //given
        List<VoucherServiceResponse> voucherServiceRespons = List.of(new VoucherServiceResponse(voucher));
        walletRepository.insert(wallet);
        customerRepository.insert(customer);
        voucherRepository.insert(voucher);

        //when
        List<VoucherServiceResponse> result = walletService.voucherList(CUSTOMER_ID);

        //then
        assertThat(result).containsExactlyElementsOf(voucherServiceRespons);
    }

}
