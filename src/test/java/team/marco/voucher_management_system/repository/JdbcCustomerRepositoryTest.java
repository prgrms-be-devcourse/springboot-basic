package team.marco.voucher_management_system.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import team.marco.voucher_management_system.configuration.TestJdbcRepositoryConfiguration;
import team.marco.voucher_management_system.model.Customer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig(TestJdbcRepositoryConfiguration.class)
class JdbcCustomerRepositoryTest {
    private static final String DELETE_FROM_CUSTOMER = "DELETE FROM customer";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcCustomerRepository repository;

    @BeforeAll
    @AfterEach
    void truncateTable() {
        jdbcTemplate.execute(DELETE_FROM_CUSTOMER);
    }

    @Nested
    @DisplayName("고객 추가 테스트")
    class TestCreate {
        @Test
        @DisplayName("고객을 추가할 수 있어야 한다.")
        void success() {
            // given
            Customer customer = generateCustomer(1000);

            // when
            int count = repository.create(customer);

            // then
            assertThat(count).isEqualTo(1);
        }

        @Test
        @DisplayName("동일한 이메일의 고객을 추가할 경우 예외가 발생한다.")
        void failToDuplicateEmail() {
            // given
            Customer customer = new Customer("test1", "test@test.test");
            Customer duplicatedEmailCustomer = new Customer("test2", "test@test.test");

            // when
            int initInsertion = repository.create(customer);

            ThrowingCallable targetMethod = () -> repository.create(duplicatedEmailCustomer);

            // then
            assertThat(initInsertion).isEqualTo(1);
            assertThatExceptionOfType(DuplicateKeyException.class).isThrownBy(targetMethod);
        }
    }

    @Test
    @DisplayName("추가한 사용자를 모두 찾을 수 있어야 한다.")
    void testFindAll() {
        // given
        int count = 33;
        List<Customer> generatedCustomers = addTestCustomers(count);

        // when
        List<Customer> customers = repository.findAll();

        // then
        assertThat(customers.size()).isEqualTo(count);

        List<UUID> retrievedIds = customers.stream()
                .map(Customer::getId)
                .toList();
        List<UUID> generatedIds = generatedCustomers.stream()
                .map(Customer::getId)
                .toList();

        assertThat(retrievedIds).containsExactlyInAnyOrderElementsOf(generatedIds);
    }

    @Test
    @DisplayName("사용자의 이름을 수정할 수 있어야 한다.")
    void testUpdate() {
        // given
        Customer generatedCustomer = generateCustomer(0);
        String name = "changedName";

        repository.create(generatedCustomer);

        // when
        generatedCustomer.changeName(name);
        repository.update(generatedCustomer);

        // then
        Optional<Customer> optionalCustomer = repository.findById(generatedCustomer.getId().toString());

        assertThat(optionalCustomer.isPresent()).isTrue();

        Customer customer = optionalCustomer.get();

        assertThat(customer.getName()).isEqualTo(generatedCustomer.getName());
        assertThat(customer.getName()).isEqualTo(name);
    }

    @Nested
    @DisplayName("고객 id 조회 테스트")
    class TestFindById {
        @Test
        @DisplayName("id가 일치하는 고객이 존재할 경우 조회할 수 있어야 한다.")
        void success() {
            // given
            int seed = 100;
            Customer existCustomer = generateCustomer(seed + 1);
            Customer notExistCustomer = generateCustomer(seed + 2);

            addTestCustomers(seed);
            repository.create(existCustomer);

            // when
            Optional<Customer> existOptionalCustomer = repository.findById(existCustomer.getId().toString());

            // then
            assertThat(existOptionalCustomer.isPresent()).isTrue();

            Customer retrievedCustomer = existOptionalCustomer.get();

            assertThat(retrievedCustomer).usingRecursiveComparison()
                    .isEqualTo(existCustomer);
        }

        @Test
        @DisplayName("id가 일치하는 고객이 없을 경우 예외를 발생해야 한다.")
        void failToNotExistId() {
            // given
            int seed = 100;
            Customer notExistCustomer = generateCustomer(seed + 1);

            addTestCustomers(seed);

            // when
            Optional<Customer> existOptionalCustomer = repository.findById(notExistCustomer.getId().toString());

            // then
            assertThat(existOptionalCustomer.isEmpty()).isTrue();
        }
    }

    @Test
    @DisplayName("특정 문자를 포함하는 이름의 사용자를 모두 조회할 수 있어야 한다.")
    void testFindByName() {
        // given
        int seed = 100;
        String queryName = "5";
        List<Customer> addedCustomers = addTestCustomers(seed);

        // when
        List<Customer> customers = repository.findByName(queryName);

        // then
        List<UUID> addedIds = addedCustomers.stream()
                .filter(customer -> customer.getName().contains(queryName))
                .map(Customer::getId)
                .toList();
        List<UUID> customerIds = customers.stream()
                .map(Customer::getId)
                .toList();

        assertThat(customerIds).containsExactlyInAnyOrderElementsOf(addedIds);
    }

    @Test
    @DisplayName("특정 문자를 포함하는 이메일의 사용자를 모두 조회할 수 있어야 한다.")
    void testFindByEmail() {
        // given
        int seed = 100;
        String queryEmail = "st5";
        List<Customer> addedCustomers = addTestCustomers(seed);

        // when
        List<Customer> customers = repository.findByEmail(queryEmail);

        // then
        List<UUID> addedIds = addedCustomers.stream()
                .filter(customer -> customer.getEmail().contains(queryEmail))
                .map(Customer::getId)
                .toList();
        List<UUID> customerIds = customers.stream()
                .map(Customer::getId)
                .toList();

        assertThat(customerIds).containsExactlyInAnyOrderElementsOf(addedIds);
    }

    private Customer generateCustomer(int seed) {
        String name = String.format("testUser%d", seed);
        String email = String.format("test%d@test.test", seed);

        return new Customer(name, email);
    }

    private List<Customer> addTestCustomers(int count) {
        List<Customer> generatedCustomers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Customer customer = generateCustomer(i);

            repository.create(customer);
            generatedCustomers.add(customer);
        }

        return generatedCustomers.stream().toList();
    }
}
