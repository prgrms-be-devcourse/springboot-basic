package com.wonu606.vouchermanager.repository.voucherwallet;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.repository.voucher.VoucherJdbcRepository;
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
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletUpdateQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.reader.VoucherWalletJdbcReader;
import com.wonu606.vouchermanager.repository.voucherwallet.reader.VoucherWalletReader;
import com.wonu606.vouchermanager.repository.voucherwallet.reader.rowmapper.VoucherWalletReaderRowMapperManager;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
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
@DisplayName("JdbcCustomerVoucherWalletRepository 테스트")
class VoucherWalletJdbcRepositoryTest {

    private VoucherWalletJdbcRepository voucherWalletJdbcRepository;
    private VoucherJdbcRepository voucherJdbcRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp() {
        VoucherWalletReader voucherWalletReader = new VoucherWalletJdbcReader(
                namedParameterJdbcTemplate,
                new VoucherWalletReaderRowMapperManager());
        VoucherWalletStore voucherWalletJdbcStore = new VoucherWalletJdbcStore(
                namedParameterJdbcTemplate);
        voucherWalletJdbcRepository = new VoucherWalletJdbcRepository(voucherWalletReader,
                voucherWalletJdbcStore);

        VoucherReader voucherReader = new VoucherJdbcReader(namedParameterJdbcTemplate,
                new VoucherReaderRowMapperManager());
        VoucherStore voucherStore = new VoucherJdbcStore(namedParameterJdbcTemplate);
        voucherJdbcRepository = new VoucherJdbcRepository(voucherReader, voucherStore);
    }

    @ParameterizedTest
    @MethodSource("givenVoucher")
    @DisplayName("findOwnedVouchersByCustomer_저장된 Customer가 있다면_매핑된 Voucher id를 반환한다.")
    void findOwnedVouchersByCustomer_SavedEmailAddress_ReturnsVoucherIds(Voucher voucher) {
        // given
        voucherJdbcRepository.insert(new VoucherInsertQuery(voucher.getClass().getSimpleName(),
                voucher.getUuid().toString(), voucher.getDiscountValue()));

        String expectedVouchedId = voucher.getUuid().toString();
        voucherWalletJdbcRepository.insert(new WalletInsertQuery(expectedVouchedId));
        String expectCustomId = "Hello@naver.com";
        voucherWalletJdbcRepository.update(
                new WalletUpdateQuery(expectCustomId, expectedVouchedId));

        // when
        List<OwnedVoucherResultSet> actualOwnedVouchersByCustomer = voucherWalletJdbcRepository.findOwnedVouchersByCustomer(
                new OwnedVouchersQuery(expectCustomId));

        // then
        assertThat(actualOwnedVouchersByCustomer).hasSize(1);
        assertThat(actualOwnedVouchersByCustomer.get(0).getVoucherId()).isEqualTo(
                expectedVouchedId);
    }

    @ParameterizedTest
    @MethodSource("givenVoucher")
    @DisplayName("findOwnedCustomersByVoucher_할당된 VoucherId라면_가지고 있는 Customer의 Email을 반환한다.")
    void findOwnedCustomersByVoucher_SavedVoucherId_ReturnsEmail(Voucher voucher) {
        // given
        voucherJdbcRepository.insert(new VoucherInsertQuery(voucher.getClass().getSimpleName(),
                voucher.getUuid().toString(), voucher.getDiscountValue()));

        String expectedVouchedId = voucher.getUuid().toString();
        voucherWalletJdbcRepository.insert(new WalletInsertQuery(expectedVouchedId));
        String expectCustomId = "Hello@naver.com";
        voucherWalletJdbcRepository.update(
                new WalletUpdateQuery(expectCustomId, expectedVouchedId));

        // when
        List<OwnedCustomerResultSet> actualOwnedCustomersByVoucher = voucherWalletJdbcRepository.findOwnedCustomersByVoucher(
                new OwnedCustomersQuery(expectedVouchedId));

        // then
        assertThat(actualOwnedCustomersByVoucher).hasSize(1);
        assertThat(actualOwnedCustomersByVoucher.get(0).getCustomerId()).isEqualTo(expectCustomId);
    }

    @ParameterizedTest
    @MethodSource("givenVoucher")
    @DisplayName("delete_저장된 VoucherId라면_삭제한다.")
    void delete_SavedVoucherId_delete(Voucher voucher) {
        // given
        voucherJdbcRepository.insert(new VoucherInsertQuery(voucher.getClass().getSimpleName(),
                voucher.getUuid().toString(), voucher.getDiscountValue()));

        String expectedVouchedId = voucher.getUuid().toString();
        voucherWalletJdbcRepository.insert(new WalletInsertQuery(expectedVouchedId));
        voucherWalletJdbcRepository.insert(new WalletInsertQuery(expectedVouchedId));

        // when
        voucherWalletJdbcRepository.delete(new WalletDeleteQuery("NULL", expectedVouchedId));
        voucherWalletJdbcRepository.delete(new WalletDeleteQuery("NULL", expectedVouchedId));

        List<OwnedCustomerResultSet> actualOwnedCustomersByVoucher = voucherWalletJdbcRepository.findOwnedCustomersByVoucher(
                new OwnedCustomersQuery(expectedVouchedId));

        // then
        assertThat(actualOwnedCustomersByVoucher).hasSize(0);
    }

    static Stream<Arguments> givenVoucher() {
        Customer customer1 = new Customer(
                new Email("Linlin@onepiece.org"), "Big Mom");

        return Stream.of(Arguments.of(customer1));
    }
}
