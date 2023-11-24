package team.marco.voucher_management_system.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private static final Faker faker = new Faker();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcCustomerRepository repository;

    @BeforeAll
    @AfterEach
    void truncateTable() {
        jdbcTemplate.execute(DELETE_FROM_CUSTOMER);
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
        Customer generatedCustomer = generateCustomer();
        String name = faker.name().name();

        repository.create(generatedCustomer);

        // when
        generatedCustomer.changeName(name);
        repository.update(generatedCustomer);

        // then
        Optional<Customer> optionalCustomer = repository.findById(generatedCustomer.getId());

        assertThat(optionalCustomer.isPresent()).isTrue();

        Customer customer = optionalCustomer.get();

        assertThat(customer.getName()).isEqualTo(generatedCustomer.getName());
        assertThat(customer.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("특정 문자를 포함하는 이름의 사용자를 모두 조회할 수 있어야 한다.")
    void testFindByName() {
        // given
        String match = "t";
        List<Customer> addedCustomers = addTestCustomers(10);

        // when
        List<Customer> customers = repository.findByName(match);

        // then
        List<UUID> addedIds = addedCustomers.stream()
                .filter(customer -> isContain(customer.getName(), match))
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
        String match = "t";
        List<Customer> addedCustomers = addTestCustomers(10);

        // when
        List<Customer> customers = repository.findByEmail(match);

        // then
        List<UUID> addedIds = addedCustomers.stream()
                .filter(customer -> isContain(customer.getEmail(), match))
                .map(Customer::getId)
                .toList();
        List<UUID> customerIds = customers.stream()
                .map(Customer::getId)
                .toList();

        assertThat(customerIds).containsExactlyInAnyOrderElementsOf(addedIds);
    }

    private Customer generateCustomer() {
        String name = faker.name().firstName();
        String email = faker.internet().emailAddress();

        return new Customer(name, email);
    }

    private List<Customer> addTestCustomers(int count) {
        Set<String> emails = new HashSet<>();
        List<Customer> generatedCustomers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Customer customer = generateCustomer();

            if (emails.contains(customer.getEmail())) {
                continue;
            }

            emails.add(customer.getEmail());
            repository.create(customer);
            generatedCustomers.add(customer);
        }

        return generatedCustomers.stream().toList();
    }

    private boolean isContain(String target, String match) {
        return target.toLowerCase().contains(match.toLowerCase());
    }

    @Nested
    @DisplayName("고객 삭제 테스트")
    class TestDeleteById {
        @Test
        @DisplayName("id가 일치하는 고객이 존재할 경우 삭제할 수 있어야 한다.")
        void success() {
            // given
            Customer existCustomer = generateCustomer();

            addTestCustomers(10);
            repository.create(existCustomer);

            // when
            int delete = repository.deleteById(existCustomer.getId());

            // then
            assertThat(delete).isEqualTo(1);
        }

        @Test
        @DisplayName("id가 일치하는 고객이 없을 경우 0을 반환한다.")
        void failToNotExistId() {
            // given
            Customer notExistCustomer = generateCustomer();

            addTestCustomers(10);

            // when
            int delete = repository.deleteById(notExistCustomer.getId());

            // then
            assertThat(delete).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("고객 추가 테스트")
    class TestCreate {
        @Test
        @DisplayName("고객을 추가할 수 있어야 한다.")
        void success() {
            // given
            Customer customer = generateCustomer();

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

    @Nested
    @DisplayName("고객 id 조회 테스트")
    class TestFindById {
        @Test
        @DisplayName("id가 일치하는 고객이 존재할 경우 조회할 수 있어야 한다.")
        void success() {
            // given
            Customer existCustomer = generateCustomer();

            addTestCustomers(10);
            repository.create(existCustomer);

            // when
            Optional<Customer> existOptionalCustomer = repository.findById(existCustomer.getId());

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
            Customer notExistCustomer = generateCustomer();

            addTestCustomers(10);

            // when
            Optional<Customer> existOptionalCustomer = repository.findById(notExistCustomer.getId());

            // then
            assertThat(existOptionalCustomer.isEmpty()).isTrue();
        }
    }
}
