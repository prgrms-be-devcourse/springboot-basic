package com.wonu606.vouchermanager.repository.customerVoucherWallet;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.CustomerVoucherWallet.CustomerVoucherWallet;
import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.EmailAddress;
import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.PercentageDiscountValue;
import com.wonu606.vouchermanager.repository.customer.JdbcCustomerResultSetRepository;
import com.wonu606.vouchermanager.repository.voucher.JdbcVoucherResultSetRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
@DisplayName("JdbcCustomerVoucherWalletRepository 테스트")
class JdbcCustomerVoucherWalletRepositoryTest {

    private JdbcCustomerVoucherWalletRepository customerVoucherWalletRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        customerVoucherWalletRepository = new JdbcCustomerVoucherWalletRepository(dataSource);

        JdbcVoucherResultSetRepository voucherResultSetRepository =
                new JdbcVoucherResultSetRepository(dataSource);
        JdbcCustomerResultSetRepository customerResultSetRepository =
                new JdbcCustomerResultSetRepository(dataSource);

        givenCustomerVouchers().forEach(
                arguments -> {
                    Customer customer = (Customer) arguments.get()[0];
                    Voucher voucher = (Voucher) arguments.get()[1];
                    customerResultSetRepository.save(customer);
                    voucherResultSetRepository.save(voucher);
                    customerVoucherWalletRepository.save(
                            new CustomerVoucherWallet(
                                    voucher.getUuid(),
                                    customer.getEmailAddress()));
                }
        );
    }

    @ParameterizedTest
    @MethodSource("givenCustomerVouchers")
    @DisplayName("findVoucherIdByCustomerEmailAddress_저장된 이메일 주소라면_매핑된 Voucher id를 반환한다.")
    void findVoucherIdByCustomerEmailAddress_SavedEmailAddress_ReturnsVoucherIds(
            Customer customer, Voucher voucher) {
        // given
        String emailAddress = customer.getEmailAddress();

        // when
        List<UUID> actualUuids =
                customerVoucherWalletRepository.findVoucherIdByCustomerEmailAddress(emailAddress);

        // then
        assertThat(actualUuids).hasSize(1);
        assertThat(actualUuids.get(0)).isEqualTo(voucher.getUuid());
    }

    @ParameterizedTest
    @MethodSource("givenCustomerVouchers")
    @DisplayName("deleteByCustomerVoucherWallet_저장된 월렛이면_해당 월렛을 제거한다.")
    void deleteByCustomerVoucherWallet_SavedWallet_Deleted(Customer customer, Voucher voucher) {
        // given
        CustomerVoucherWallet customerVoucherWallet =
                new CustomerVoucherWallet(voucher.getUuid(), customer.getEmailAddress());

        // when
        customerVoucherWalletRepository.deleteByCustomerVoucherWallet(customerVoucherWallet);

        // then
        List<String> emailAddresses =
                customerVoucherWalletRepository.findEmailAddressesByVoucherId(voucher.getUuid());
        assertThat(emailAddresses).hasSize(0);
    }

    @ParameterizedTest
    @MethodSource("givenCustomerVouchers")
    @DisplayName("findEmailAddressesByVoucherId_할당된 VoucherId라면_가지고 있는 Customer의 EmailAddress 반환한다.")
    void findEmailAddressesByVoucherId_SavedVoucherId_ReturnsEmailAddresses(
            Customer customer, Voucher voucher) {
        // given
        UUID voucherId = voucher.getUuid();

        // when
        List<String> emailAddresses =
                customerVoucherWalletRepository.findEmailAddressesByVoucherId(voucherId);

        // then
        assertThat(emailAddresses).hasSize(1);
        assertThat(emailAddresses.get(0)).isEqualTo(customer.getEmailAddress());
    }

    static Stream<Arguments> givenCustomerVouchers() {
        Customer customer1 = new Customer(
                new EmailAddress("Linlin@onepiece.org"), "Big Mom");
        Customer customer2 = new Customer(
                new EmailAddress("loopy@onepiece.org"), "Pirate King");

        UUID uuid1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID uuid2 = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        Voucher voucher1 = new PercentageVoucher(
                uuid1, new PercentageDiscountValue(50.0));
        Voucher voucher2 = new FixedAmountVoucher(
                uuid2, new FixedAmountValue(50.0));

        return Stream.of(
                Arguments.of(customer1, voucher1),
                Arguments.of(customer2, voucher2));
    }
}
