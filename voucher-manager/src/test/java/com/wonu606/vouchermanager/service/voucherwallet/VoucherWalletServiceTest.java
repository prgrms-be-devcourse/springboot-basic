package com.wonu606.vouchermanager.service.voucherwallet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.wonu606.vouchermanager.repository.voucherwallet.VoucherWalletRepository;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletRegisterQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.service.customer.param.WalletRegisterParam;
import com.wonu606.vouchermanager.service.voucher.VoucherService;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.service.voucher.result.VoucherResult;
import com.wonu606.vouchermanager.service.voucherwallet.converter.VoucherWalletServiceConverterManager;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedCustomerResult;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("VoucherWalletService 테스트")
class VoucherWalletServiceTest {

    @Mock
    private VoucherService voucherService;
    @Mock
    private VoucherWalletRepository repository;

    private VoucherWalletService voucherWalletService;

    @BeforeEach
    void setUp() {
        VoucherWalletServiceConverterManager converterManager = new VoucherWalletServiceConverterManager();
        voucherWalletService = new VoucherWalletService(voucherService, repository,
                converterManager);
    }

    @Test
    @DisplayName("findOwnedVouchersByCustomer_유효한 CustomerId라면_Voucher List를 반환한다.")
    void findOwnedVouchersByCustomer_WithValidCustomerId_ReturnsListOfVouchers() {
        // Given
        String voucherUUID = "42424242-4242-4242-4242-424242424242";
        ;
        given(repository.findOwnedVouchersByCustomer(any())).willReturn(
                List.of(new OwnedVoucherResultSet(voucherUUID)));

        OwnedVoucherResult expectedResult = new OwnedVoucherResult(voucherUUID);

        OwnedVouchersParam param = new OwnedVouchersParam("test@test.org");

        // When
        List<OwnedVoucherResult> actualResult = voucherWalletService.findOwnedVouchersByCustomer(
                param);

        // Then
        assertThat(actualResult).hasSize(1);
        assertThat(actualResult).usingRecursiveFieldByFieldElementComparator()
                .contains(expectedResult);
    }

    @Test
    @DisplayName("deleteWallet_유효한 파라미터라면_Wallet을 제거한다.")
    void deleteWallet_WithValidParam_WalletIsDeleted() {
        // Given
        WalletDeleteParam param = new WalletDeleteParam(UUID.randomUUID(), "test@test.org");
        WalletDeleteQuery expectedQuery = new WalletDeleteQuery(param.getCustomerEmail(),
                param.getVoucherUuid().toString());

        // When
        voucherWalletService.deleteWallet(param);

        // Then
        ArgumentCaptor<WalletDeleteQuery> argument = ArgumentCaptor.forClass(
                WalletDeleteQuery.class);
        then(repository).should().delete(argument.capture());
        WalletDeleteQuery actual = argument.getValue();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("assignWallet_유효한 파라미터라면_Wallet을 할당한다")
    void assignWallet_WithValidParam_WalletIsAssigned() {
        // Given
        WalletAssignParam param = new WalletAssignParam(UUID.randomUUID());
        WalletInsertQuery expectedQuery = new WalletInsertQuery(param.getVoucherId().toString());
        given(repository.insert(any())).willReturn(new WalletInsertResultSet(1));

        // When
        voucherWalletService.assignWallet(param);

        // Then
        ArgumentCaptor<WalletInsertQuery> argument = ArgumentCaptor.forClass(
                WalletInsertQuery.class);
        then(repository).should().insert(argument.capture());
        WalletInsertQuery actual = argument.getValue();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("findOwnedCustomersByVoucher_유효한 파라미터라면_Customer List를 반환한다.")
    void findOwnedCustomersByVoucher_WithValidParam_ReturnsListOfCustomers() {
        // Given
        OwnedCustomersParam param = new OwnedCustomersParam(UUID.randomUUID());

        OwnedCustomerResultSet resultSet = new OwnedCustomerResultSet("test@test.org");
        given(repository.findOwnedCustomersByVoucher(any())).willReturn(List.of(resultSet));
        OwnedCustomerResult expectedResult = new OwnedCustomerResult(resultSet.getCustomerId());

        // When
        List<OwnedCustomerResult> actualResult = voucherWalletService.findOwnedCustomersByVoucher(
                param);

        // Then
        assertThat(actualResult).hasSize(1);
        assertThat(actualResult).usingRecursiveFieldByFieldElementComparator()
                .contains(expectedResult);
    }


    @Test
    @DisplayName("createVoucher_유효한 파라미터라면_Voucher를 생성한다.")
    void createVoucher_WithValidParam_CreatedVoucher() {
        // Given
        VoucherCreateParam param = new VoucherCreateParam("voucherType", 50.0);

        // When
        voucherWalletService.createVoucher(param);

        // Then
        ArgumentCaptor<VoucherCreateParam> argument = ArgumentCaptor.forClass(
                VoucherCreateParam.class);
        then(voucherService).should().createVoucher(argument.capture());
        VoucherCreateParam actual = argument.getValue();
        assertThat(actual).usingRecursiveComparison().isEqualTo(param);
    }

    @Test
    @DisplayName("getVoucherList_데이터가 있을 경우_Voucher List를 반환한다.")
    void getVoucherList_WhenDataExists_ReturnsListOfVouchers() {
        // Given
        List<VoucherResult> expectedResult = List.of(
                new VoucherResult("voucherId", "voucherType", 50.0));
        given(voucherService.getVoucherList()).willReturn(expectedResult);

        // When
        List<VoucherResult> actualResult = voucherWalletService.getVoucherList();

        // Then
        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("registerToWallet_유효한 파라미터라면_Wallet을 할당한다.")
    void registerToWallet_WithValidParam_WalletIsRegistered() {
        // Given
        WalletRegisterParam param = new WalletRegisterParam(UUID.randomUUID(), "customerId");
        WalletRegisterQuery expectedQuery = new WalletRegisterQuery(param.getCustomerId(),
                param.getVoucherId().toString());

        // When
        voucherWalletService.registerToWallet(param);

        // Then
        ArgumentCaptor<WalletRegisterQuery> argument = ArgumentCaptor.forClass(
                WalletRegisterQuery.class);
        then(repository).should().register(argument.capture());
        WalletRegisterQuery actual = argument.getValue();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedQuery);
    }
}
