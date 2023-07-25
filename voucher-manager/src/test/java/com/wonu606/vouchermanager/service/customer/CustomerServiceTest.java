package com.wonu606.vouchermanager.service.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import com.wonu606.vouchermanager.repository.customer.CustomerRepository;
import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.service.customer.converter.CustomerServiceConverterManager;
import com.wonu606.vouchermanager.service.customer.factory.CustomerFactory;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import com.wonu606.vouchermanager.service.customer.param.WalletRegisterParam;
import com.wonu606.vouchermanager.service.customer.result.CustomerResult;
import com.wonu606.vouchermanager.service.voucherwallet.VoucherWalletService;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@DisplayName("CustomerService 테스트")
class CustomerServiceTest {

    private VoucherWalletService voucherWalletService;
    private CustomerRepository repository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        repository = mock(CustomerRepository.class);
        voucherWalletService = mock(VoucherWalletService.class);
        customerService = new CustomerService(voucherWalletService, repository,
                new CustomerFactory(), new CustomerServiceConverterManager());
    }

    @Test
    @DisplayName("createCustomer_유효한 파라미터이면_Customer를 생성한다.")
    void createCustomer_WithValidParameters_CustomerIsCreated() {
        // Given
        String customerEmail = "test@test.org";
        String customerNickname = "test";
        CustomerCreateParam param = new CustomerCreateParam(customerEmail, customerNickname);
        given(repository.insert(any())).willReturn(new CustomerCreateResultSet(1));
        CustomerCreateQuery excepted = new CustomerCreateQuery(customerEmail, customerNickname);

        // When
        customerService.createCustomer(param);

        // Then
        ArgumentCaptor<CustomerCreateQuery> argument = ArgumentCaptor.forClass(
                CustomerCreateQuery.class);
        then(repository).should().insert(argument.capture());
        CustomerCreateQuery actual = argument.getValue();
        assertThat(actual).usingRecursiveComparison().isEqualTo(excepted);
    }

    @Test
    @DisplayName("getCustomerList_존재하는 Customer라면_CustomerList를 반환한다.")
    void getCustomerList_WhenCustomersExist_ReturnsListOfCustomers() {
        // Given
        List<CustomerResultSet> resultSets = List.of(
                new CustomerResultSet("test@test.com", "nickname"));
        given(repository.findAll()).willReturn(resultSets);

        List<CustomerResult> expectedResult = List.of(
                new CustomerResult("test@test.com", "nickname"));

        // When
        List<CustomerResult> actualResult = customerService.getCustomerList();

        // Then
        assertThat(actualResult).hasSize(1);
        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("findOwnedVouchersByCustomer_유효한 CustomerId라면_Voucher List를 반환한다.")
    void findOwnedVouchersByCustomer_WithValidCustomerId_ReturnsListOfVouchers() {
        // Given
        List<OwnedVoucherResult> expectedResults = List.of(new OwnedVoucherResult("voucherId"));
        given(voucherWalletService.findOwnedVouchersByCustomer(any())).willReturn(expectedResults);
        OwnedVouchersParam expected = new OwnedVouchersParam("customerId");

        // When
        customerService.findOwnedVouchersByCustomer(expected);

        // Then
        ArgumentCaptor<OwnedVouchersParam> argument = ArgumentCaptor.forClass(
                OwnedVouchersParam.class);
        then(voucherWalletService).should().findOwnedVouchersByCustomer(argument.capture());
        OwnedVouchersParam actual = argument.getValue();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("deleteWallet_유효한 파라미터라면_월렛을 제거한다.")
    void deleteWallet_WithValidParam_WalletIsDeleted() {
        // Given
        UUID voucherUuid = UUID.randomUUID();
        WalletDeleteParam expected = new WalletDeleteParam(voucherUuid, "customerId");

        // When
        customerService.deleteWallet(expected);

        // Then
        ArgumentCaptor<WalletDeleteParam> argument = ArgumentCaptor.forClass(
                WalletDeleteParam.class);
        then(voucherWalletService).should().deleteWallet(argument.capture());
        WalletDeleteParam actual = argument.getValue();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("registerToWallet_유효한 파라미터라면_Wallet에 등록한다.")
    void registerToWallet_WithValidParam_WalletIsRegistered() {
        // Given
        UUID voucherUuid = UUID.randomUUID();
        WalletRegisterParam expected = new WalletRegisterParam(voucherUuid, "customerId");

        // When
        customerService.registerToWallet(expected);

        // Then
        ArgumentCaptor<WalletRegisterParam> argument = ArgumentCaptor.forClass(
                WalletRegisterParam.class);
        then(voucherWalletService).should().registerToWallet(argument.capture());
        WalletRegisterParam actual = argument.getValue();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
