package devcourse.springbootbasic.repository.customer;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.util.UUIDUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        Customer customer1 = new Customer(UUIDUtil.generateRandomUUID(), "Platypus", false);
        Customer customer2 = new Customer(UUIDUtil.generateRandomUUID(), "Ogu", true);

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
        Customer newCustomer = new Customer(UUIDUtil.generateRandomUUID(), "Ogu", true);
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
        UUID customerId = UUIDUtil.generateRandomUUID();
        Customer customer = new Customer(customerId, "Platypus", false);
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
        UUID customerId = UUIDUtil.generateRandomUUID();
        Customer customer = new Customer(customerId, "Platypus", false);
        customerRepository.save(customer);

        // When
        customer.applyBlacklist();
        int updatedRows = customerRepository.update(customer);

        // Then
        assertThat(updatedRows).isEqualTo(1);
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().isBlacklisted()).isTrue();
    }

    @Test
    @DisplayName("고객 정보가 없는 경우 업데이트하지 않습니다.")
    void testUpdateCustomerWhenCustomerDoesNotExist() {
        // Given
        UUID customerId = UUIDUtil.generateRandomUUID();
        Customer customer = new Customer(customerId, "Platypus", false);

        // When
        int updatedRows = customerRepository.update(customer);

        // Then
        assertThat(updatedRows).isZero();
    }
}
