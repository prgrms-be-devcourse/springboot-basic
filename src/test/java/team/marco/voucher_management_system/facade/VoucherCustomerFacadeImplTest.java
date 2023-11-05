package team.marco.voucher_management_system.facade;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import team.marco.voucher_management_system.configuration.TestJdbcRepositoryConfiguration;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.model.FixedAmountVoucher;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.repository.CustomerRepository;
import team.marco.voucher_management_system.repository.VoucherRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig(TestJdbcRepositoryConfiguration.class)
class VoucherCustomerFacadeImplTest {
    private static final String DELETE_FROM_VOUCHER = "DELETE FROM voucher;";
    private static final String DELETE_FROM_CUSTOMER = "DELETE FROM customer";
    private static final Faker faker = new Faker();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VoucherCustomerFacade voucherCustomerFacade;

    @BeforeAll
    @AfterEach
    void truncateTable() {
        jdbcTemplate.execute(DELETE_FROM_VOUCHER);
        jdbcTemplate.execute(DELETE_FROM_CUSTOMER);
    }

    @Nested
    @DisplayName("Voucher 조회 테스트")
    class TestHasVoucher {
        @Test
        @DisplayName("id가 일치하는 Voucher가 존재하면 true를 반환한다.")
        void found() {
            // given
            Voucher voucher = generateVoucher();

            voucherRepository.save(voucher);

            // when
            boolean hasVoucher = voucherCustomerFacade.hasVoucher(voucher.getId());

            // then
            assertThat(hasVoucher).isTrue();
        }

        @Test
        @DisplayName("id가 일치하는 Voucher가 없으면 false를 반환한다.")
        void notFound() {
            // given
            Voucher voucher = generateVoucher();

            // when
            boolean hasVoucher = voucherCustomerFacade.hasVoucher(voucher.getId());

            // then
            assertThat(hasVoucher).isFalse();
        }
    }

    @Nested
    @DisplayName("Voucher 조회 테스트")
    class TestHasCustomer {
        @Test
        @DisplayName("id가 일치하는 고객이 존재하면 true를 반환한다.")
        void found() {
            // given
            Customer customer = generateCustomer();

            customerRepository.create(customer);

            // when
            boolean hasCustomer = voucherCustomerFacade.hasCustomer(customer.getId());

            // then
            assertThat(hasCustomer).isTrue();
        }

        @Test
        @DisplayName("id가 일치하는 고객이 없으면 false를 반환한다.")
        void notFound() {
            // given
            Customer customer = generateCustomer();

            // when
            boolean hasCustomer = voucherCustomerFacade.hasCustomer(customer.getId());

            // then
            assertThat(hasCustomer).isFalse();
        }
    }

    @Test
    @DisplayName("id 리스트에 있는 모든 Voucher를 조회할 수 있다.")
    void testGetVouchers() {
        // given
        List<Voucher> vouchers = List.of(
                generateVoucher(),
                generateVoucher(),
                generateVoucher());
        List<UUID> voucherIds = vouchers.stream()
                .map(Voucher::getId)
                .toList();

        vouchers.forEach(voucherRepository::save);

        // when
        List<Voucher> retrievedVouchers = voucherCustomerFacade.getVouchers(voucherIds);

        // then
        List<UUID> retrievedVoucherIds = retrievedVouchers.stream()
                .map(Voucher::getId)
                .toList();

        assertThat(retrievedVoucherIds).containsExactlyInAnyOrderElementsOf(voucherIds);
    }

    @Test
    @DisplayName("id 리스트에 있는 모든 고객을 조회할 수 있다.")
    void testGetCustomers() {
        // given
        List<Customer> customers = List.of(
                generateCustomer(),
                generateCustomer(),
                generateCustomer());
        List<UUID> customerIds = customers.stream()
                .map(Customer::getId)
                .toList();

        customers.forEach(customerRepository::create);

        // when
        List<Customer> retrievedCustomers = voucherCustomerFacade.getCustomers(customerIds);

        // then
        List<UUID> retrievedCustomerIds = retrievedCustomers.stream()
                .map(Customer::getId)
                .toList();

        assertThat(retrievedCustomerIds).containsExactlyInAnyOrderElementsOf(customerIds);
    }

    private Voucher generateVoucher() {
        return new FixedAmountVoucher(100);
    }

    private Customer generateCustomer() {
        String name = faker.name().name();
        String email = faker.internet().emailAddress();

        return new Customer(name, email);
    }
}
