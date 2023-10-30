package team.marco.voucher_management_system.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import team.marco.voucher_management_system.configuration.TestJdbcRepositoryConfiguration;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.model.FixedAmountVoucher;
import team.marco.voucher_management_system.model.Voucher;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig(TestJdbcRepositoryConfiguration.class)
class JdbcWalletRepositoryTest {
    private static final String DELETE_FROM_WALLET = "DELETE FROM wallet";
    private static final String DELETE_FROM_VOUCHER = "DELETE FROM voucher";
    private static final String DELETE_FROM_CUSTOMER = "DELETE FROM customer";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcWalletRepository walletRepository;
    @Autowired
    private JdbcVoucherRepository voucherRepository;
    @Autowired
    private JdbcCustomerRepository customerRepository;

    @BeforeAll
    void validateReferenceRepository() {
        // To test wallet repository, implement jdbc and customer repository
        truncateTables();

        validate(voucherRepository);
        validate(customerRepository);

        truncateTables();
    }

    @AfterEach
    void truncateTables() {
        jdbcTemplate.execute(DELETE_FROM_WALLET);
        jdbcTemplate.execute(DELETE_FROM_VOUCHER);
        jdbcTemplate.execute(DELETE_FROM_CUSTOMER);
    }

    @Nested
    @DisplayName("고객 지갑 쿠폰 추가 테스트")
    class TestLink {
        @Test
        @DisplayName("고객의 지갑에 쿠폰을 추가할 수 있다.")
        void success() {
            // given
            Voucher voucher = addVoucher();
            Customer customer = addCustomer(0);

            // when
            int count = walletRepository.link(customer.getId().toString(), voucher.getId().toString());

            // then
            assertThat(count).isEqualTo(1);
        }

        @Test
        @DisplayName("이미 추가된 쿠폰은 지갑에 추가될 수 없다.")
        void failToAlreadyExist() {
            // given
            Voucher voucher = addVoucher();
            Customer customer = addCustomer(0);

            walletRepository.link(customer.getId().toString(), voucher.getId().toString());

            // when
            ThrowingCallable targetMethod = () -> walletRepository.link(
                    customer.getId().toString(), voucher.getId().toString());

            // then
            assertThatExceptionOfType(DataIntegrityViolationException.class)
                    .isThrownBy(targetMethod);
        }
    }

    @Nested
    @DisplayName("고객 지갑 쿠폰 반납 테스트")
    class TestUnlink {
        @Test
        @DisplayName("고객의 지갑에 쿠폰을 반납할 수 있다.")
        void success() {
            // given
            Voucher voucher = addVoucher();
            Customer customer = addCustomer(0);

            walletRepository.link(customer.getId().toString(), voucher.getId().toString());

            // when
            int count = walletRepository.unlink(customer.getId().toString(), voucher.getId().toString());

            // then
            assertThat(count).isEqualTo(1);
        }

        @Test
        @DisplayName("지갑에 없는 쿠폰은 지갑에 반납될 수 없다.")
        void failToNotExist() {
            // given
            Voucher voucher = addVoucher();
            Customer customer = addCustomer(0);

            // when
            int count = walletRepository.unlink(customer.getId().toString(), voucher.getId().toString());

            // then
            assertThat(count).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("특정 고객 지갑에 있는 모든 쿠폰을 조회할 수 있다.")
    void getVoucherIds() {
        // given
        UUID customerId = addCustomer(0).getId();
        List<UUID> voucherIds = List.of(addVoucher().getId(), addVoucher().getId(), addVoucher().getId());

        voucherIds.forEach(voucherId -> walletRepository.link(customerId.toString(), voucherId.toString()));

        // when
        List<UUID> retrievedIds = walletRepository.getVoucherIds(customerId.toString());

        // then
        assertThat(retrievedIds).containsExactlyInAnyOrderElementsOf(voucherIds);
    }

    @Test
    @DisplayName("특정 쿠폰을 보유한 모든 사용자를 조회할 수 있다.")
    void getCustomerIds() {
        // given
        UUID voucherId = addVoucher().getId();
        List<UUID> customerIds = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            UUID customerId = addCustomer(i).getId();

            customerIds.add(customerId);
            walletRepository.link(customerId.toString(), voucherId.toString());
        }

        // when
        List<UUID> retrievedIds = walletRepository.getCustomerIds(voucherId.toString());

        // then
        assertThat(retrievedIds).containsExactlyInAnyOrderElementsOf(customerIds);
    }

    private void validate(VoucherRepository voucherRepository) {
        Voucher voucher = addVoucher();

        List<UUID> voucherIds = voucherRepository.findAll()
                .stream()
                .map(Voucher::getId)
                .toList();

        assert voucherIds.contains(voucher.getId());
    }

    private void validate(CustomerRepository customerRepository) {
        Customer customer = addCustomer(0);

        List<UUID> customerIds = customerRepository.findAll()
                .stream()
                .map(Customer::getId)
                .toList();

        assert customerIds.contains(customer.getId());
    }

    private Voucher addVoucher() {
        Voucher voucher = new FixedAmountVoucher(100);

        voucherRepository.save(voucher);

        return voucher;
    }

    private Customer addCustomer(int seed) {
        Customer customer = new Customer("test", "test%d@test".formatted(seed));

        customerRepository.create(customer);

        return customer;
    }
}
