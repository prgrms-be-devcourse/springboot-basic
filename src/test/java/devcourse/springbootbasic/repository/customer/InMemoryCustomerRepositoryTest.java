package devcourse.springbootbasic.repository.customer;

import devcourse.springbootbasic.domain.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static devcourse.springbootbasic.TestDataFactory.generateBlacklistCustomer;
import static devcourse.springbootbasic.TestDataFactory.generateNotBlacklistCustomers;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryCustomerRepositoryTest {

    private InMemoryCustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = new InMemoryCustomerRepository();
    }

    @Test
    @DisplayName("블랙리스트 고객 조회 시 블랙리스트 된 고객만 반환합니다.")
    void testFindAllBlacklistedCustomers() {
        // Given
        Customer customer1 = generateNotBlacklistCustomers("Platypus");
        Customer customer2 = generateBlacklistCustomer("Ogu");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // When
        List<Customer> blacklistedCustomers = customerRepository.findAllBlacklistedCustomers();

        // Then
        assertThat(blacklistedCustomers).hasSize(1);
        assertThat(blacklistedCustomers.get(0).isBlacklisted()).isTrue();
    }

    @Test
    @DisplayName("고객 생성 시 생성된 고객을 반환합니다.")
    void testSaveCustomer() {
        // When
        Customer newCustomer = generateBlacklistCustomer("Ogu");
        Customer savedCustomer = customerRepository.save(newCustomer);

        // Then
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo(newCustomer.getName());
        assertThat(savedCustomer.isBlacklisted()).isTrue();
    }

    @Test
    @DisplayName("고객 ID로 고객을 조회합니다.")
    void testFindById() {
        // Given
        Customer customer = generateNotBlacklistCustomers("Platypus");
        UUID customerId = customer.getId();
        customerRepository.save(customer);

        // When
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);

        // Then
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.orElseThrow().getId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("고객 정보를 업데이트합니다.")
    void testUpdateCustomer() {
        // Given
        Customer customer = generateNotBlacklistCustomers("Platypus");
        UUID customerId = customer.getId();
        customerRepository.save(customer);

        // When
        customer.applyBlacklist();
        boolean updateResult = customerRepository.update(customer);

        // Then
        assertThat(updateResult).isTrue();
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().isBlacklisted()).isTrue();
    }

    @Test
    @DisplayName("고객 정보가 없는 경우 업데이트하지 않습니다.")
    void testUpdateCustomerWhenCustomerDoesNotExist() {
        // Given
        Customer customer = generateNotBlacklistCustomers("Platypus");

        // When
        boolean updateResult = customerRepository.update(customer);

        // Then
        assertThat(updateResult).isFalse();
    }
}
