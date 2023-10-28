package devcourse.springbootbasic.repository.customer;

import devcourse.springbootbasic.TestDataFactory;
import devcourse.springbootbasic.domain.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class JdbcCustomerRepositoryTest {

    @Autowired
    private JdbcCustomerRepository customerRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        String query = """
                DELETE FROM customer
                """;
        jdbcTemplate.update(query, (SqlParameterSource) null);
    }

    @Test
    @DisplayName("블랙리스트인 고객을 조회할 수 있습니다.")
    void testFindAllBlacklistedCustomers() {
        // Given
        Customer notBlacklistedCustomer = TestDataFactory.generateNotBlacklistCustomers("Platypus");
        Customer blacklistedCustomer = TestDataFactory.generateBlacklistCustomer("Ogu");
        customerRepository.save(notBlacklistedCustomer);
        customerRepository.save(blacklistedCustomer);

        // When
        List<Customer> blacklistedCustomers = customerRepository.findAllBlacklistedCustomers();

        // Then
        assertThat(blacklistedCustomers).hasSize(1);
    }

    @Test
    @DisplayName("고객을 저장할 수 있습니다.")
    void testSaveCustomer() {
        // Given
        Customer newCustomer = TestDataFactory.generateBlacklistCustomer("Ogu");
        Customer savedCustomer = customerRepository.save(newCustomer);

        // When
        Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).orElse(null);

        // Then
        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getId()).isEqualTo(savedCustomer.getId());
    }

    @Test
    @DisplayName("고객 ID로 고객을 조회할 수 있습니다.")
    void testFindById() {
        // Given
        Customer customer = TestDataFactory.generateNotBlacklistCustomers("Platypus");
        UUID customerId = customer.getId();
        customerRepository.save(customer);

        // When
        Optional<Customer> findCustomer = customerRepository.findById(customerId);

        // Then
        assertThat(findCustomer).isPresent();
        assertThat(findCustomer.get().getId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("고객을 수정할 수 있습니다.")
    void testUpdateCustomer() {
        // Given
        Customer customer = TestDataFactory.generateNotBlacklistCustomers("Platypus");
        UUID customerId = customer.getId();
        customerRepository.save(customer);

        // When
        Customer findCustomer = customerRepository.findById(customerId).orElseThrow();
        int updatedRows = customerRepository.update(findCustomer.applyBlacklist());

        // Then
        assertThat(updatedRows).isEqualTo(1);
        assertThat(findCustomer.isBlacklisted()).isTrue();
    }

    @Test
    @DisplayName("없는 고객을 수정하면 0을 반환합니다.")
    void testUpdateCustomerFail() {
        // Given
        customerRepository.save(TestDataFactory.generateNotBlacklistCustomers("Customer 1"));

        // When
        Customer customer = TestDataFactory.generateBlacklistCustomer("Customer 2");
        int updatedRows = customerRepository.update(customer);

        // Then
        assertThat(updatedRows).isZero();
    }
}
