package com.wonu606.vouchermanager.repository.voucherwallet;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.repository.voucher.VoucherJdbcRepository;
import com.wonu606.vouchermanager.repository.voucher.VoucherRepository;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import com.wonu606.vouchermanager.repository.voucher.reader.VoucherJdbcReader;
import com.wonu606.vouchermanager.repository.voucher.reader.VoucherReader;
import com.wonu606.vouchermanager.repository.voucher.reader.rowmapper.VoucherReaderRowMapperManager;
import com.wonu606.vouchermanager.repository.voucher.store.VoucherJdbcStore;
import com.wonu606.vouchermanager.repository.voucher.store.VoucherStore;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedCustomersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletRegisterQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.reader.VoucherWalletJdbcReader;
import com.wonu606.vouchermanager.repository.voucherwallet.reader.VoucherWalletReader;
import com.wonu606.vouchermanager.repository.voucherwallet.reader.rowmapper.VoucherWalletReaderRowMapperManager;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.store.VoucherWalletJdbcStore;
import com.wonu606.vouchermanager.repository.voucherwallet.store.VoucherWalletStore;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@DisplayName("CustomerVoucherWalletJdbcRepository 테스트")
class VoucherWalletJdbcRepositoryTest {

    private VoucherWalletRepository voucherWalletRepository;
    private VoucherRepository voucherRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp() {
        VoucherWalletReader voucherWalletReader = new VoucherWalletJdbcReader(
                namedParameterJdbcTemplate,
                new VoucherWalletReaderRowMapperManager());
        VoucherWalletStore voucherWalletJdbcStore = new VoucherWalletJdbcStore(
                namedParameterJdbcTemplate);
        voucherWalletRepository = new VoucherWalletJdbcRepository(voucherWalletReader,
                voucherWalletJdbcStore);

        VoucherReader voucherReader = new VoucherJdbcReader(namedParameterJdbcTemplate,
                new VoucherReaderRowMapperManager());
        VoucherStore voucherStore = new VoucherJdbcStore(namedParameterJdbcTemplate);
        voucherRepository = new VoucherJdbcRepository(voucherReader, voucherStore);
    }

    @ParameterizedTest
    @MethodSource("givenVoucherInsertQuery")
    @DisplayName("findOwnedVouchersByCustomer_존재하는 Customer라면_소유한 VoucherList 반환한다.")
    void findOwnedVouchersByCustomer_ExistingCustomer_ReturnOwnedVouchers(
            VoucherInsertQuery voucherInsertQuery) {
        // Given
        voucherRepository.insert(voucherInsertQuery);
        voucherWalletRepository.insert(new WalletInsertQuery(voucherInsertQuery.getVoucherId()));

        OwnedVouchersQuery ownedVouchersQuery = new OwnedVouchersQuery("test@test.org");
        voucherWalletRepository.register(
                new WalletRegisterQuery(ownedVouchersQuery.getCustomerId(),
                        voucherInsertQuery.getVoucherId()));

        OwnedVoucherResultSet ownedVoucherResultSet = new OwnedVoucherResultSet(
                voucherInsertQuery.getVoucherId());

        // When
        List<OwnedVoucherResultSet> actual = voucherWalletRepository.findOwnedVouchersByCustomer(
                ownedVouchersQuery);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(List.of(ownedVoucherResultSet));
    }

    @ParameterizedTest
    @MethodSource("givenVoucherInsertQuery")
    @DisplayName("findOwnedCustomersByVoucher_존재하는 Voucher라면_소유한 CustomerList 반환한다.")
    void findOwnedCustomersByVoucher_ExistingVoucher_ReturnOwnedCustomers(
            VoucherInsertQuery voucherInsertQuery) {
        // Given
        voucherRepository.insert(voucherInsertQuery);
        voucherWalletRepository.insert(new WalletInsertQuery(voucherInsertQuery.getVoucherId()));
        OwnedCustomersQuery ownedCustomersQuery = new OwnedCustomersQuery(
                voucherInsertQuery.getVoucherId());
        String customerId = "test@test.org";
        voucherWalletRepository.register(
                new WalletRegisterQuery(customerId, voucherInsertQuery.getVoucherId()));

        // When
        List<OwnedCustomerResultSet> actual = voucherWalletRepository.findOwnedCustomersByVoucher(
                ownedCustomersQuery);

        // Then
        assertThat(actual).hasSize(1);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(new OwnedCustomerResultSet(customerId)));
    }

    @ParameterizedTest
    @MethodSource("givenVoucherInsertQuery")
    @DisplayName("delete_존재하는 Wallet이라면_삭제한다.")
    void delete_ExistingWallet_WalletIsDeleted(VoucherInsertQuery voucherInsertQuery) {
        // Given
        voucherRepository.insert(voucherInsertQuery);
        voucherWalletRepository.insert(new WalletInsertQuery(voucherInsertQuery.getVoucherId()));
        OwnedCustomersQuery ownedCustomersQuery = new OwnedCustomersQuery(
                voucherInsertQuery.getVoucherId());
        String customerId = "test@test.org";
        voucherWalletRepository.register(
                new WalletRegisterQuery(customerId, voucherInsertQuery.getVoucherId()));
        WalletDeleteQuery walletDeleteQuery = new WalletDeleteQuery(customerId,
                voucherInsertQuery.getVoucherId());

        // When
        voucherWalletRepository.delete(walletDeleteQuery);
        List<OwnedCustomerResultSet> actual = voucherWalletRepository.findOwnedCustomersByVoucher(
                ownedCustomersQuery);

        // Then
        assertThat(actual).hasSize(0);
    }

    @ParameterizedTest
    @MethodSource("givenVoucherInsertQuery")
    @DisplayName("insert_새로 기입_기입한 정보가 저장된다.")
    void insert_NewEntry_EntryIsInserted(VoucherInsertQuery voucherInsertQuery) {
        // Given
        voucherRepository.insert(voucherInsertQuery);
        voucherWalletRepository.insert(new WalletInsertQuery(voucherInsertQuery.getVoucherId()));
        WalletInsertResultSet expected = new WalletInsertResultSet(1);
        WalletInsertQuery walletInsertQuery = new WalletInsertQuery(
                voucherInsertQuery.getVoucherId());

        // When
        WalletInsertResultSet actual = voucherWalletRepository.insert(walletInsertQuery);

        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("givenVoucherInsertQuery")
    @DisplayName("register_존재하는 빈 Wallet이라면_기입한 정보가 업데이트된다.")
    public void register_ExistingEmptyWallet_EntryIsRegistered(
            VoucherInsertQuery voucherInsertQuery) {
        // Given
        voucherRepository.insert(voucherInsertQuery);
        voucherWalletRepository.insert(new WalletInsertQuery(voucherInsertQuery.getVoucherId()));
        String customerId = "test@test.org";
        WalletRegisterQuery walletRegisterQuery = new WalletRegisterQuery(customerId,
                voucherInsertQuery.getVoucherId());
        OwnedCustomerResultSet expectedResultSet = new OwnedCustomerResultSet(customerId);

        // When
        voucherWalletRepository.register(walletRegisterQuery);
        List<OwnedCustomerResultSet> actualList = voucherWalletRepository.findOwnedCustomersByVoucher(
                new OwnedCustomersQuery(voucherInsertQuery.getVoucherId()));

        // Then
        assertThat(actualList).usingRecursiveFieldByFieldElementComparator()
                .contains(expectedResultSet);
    }

    static Stream<Arguments> givenVoucherInsertQuery() {
        VoucherInsertQuery voucherInsertQuery = new VoucherInsertQuery("fixed",
                "eaea93d1-08e4-4311-ad44-7b6a626c1a71", 10.0);

        return Stream.of(Arguments.of(voucherInsertQuery));
    }
}
