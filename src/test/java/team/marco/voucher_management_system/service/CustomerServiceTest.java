package team.marco.voucher_management_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import team.marco.voucher_management_system.domain.customer.Customer;
import team.marco.voucher_management_system.repository.custromer.CustomerRepository;
import team.marco.voucher_management_system.service.customer.CustomerService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        String query = "DELETE FROM customers";
        jdbcTemplate.update(query);
    }

    @DisplayName("사용자 ID로 사용자를 조회할 수 있습니다.")
    @Test
    void findCustomerById() {
        // given
        Customer customer = createCustomer("test", "test@gmail.com");
        customerRepository.insert(customer);

        // when
        Customer found = customerService.findCustomerById(customer.getId());

        // then
        assertThat(found.getId()).isEqualTo(customer.getId());
        assertThat(found.getName()).isEqualTo(customer.getName());
        assertThat(found.getEmail()).isEqualTo(customer.getEmail());
    }

    @DisplayName("사용자를 email로 조회할 수 있다.")
    @Test
    void findCustomerByEmail() {
        // given
        String email = "test@gmail.com";
        Customer customer = createCustomer("test", email);
        customerRepository.insert(customer);

        // when
        Customer found = customerService.findCustomerByEmail(email);

        // then
        assertThat(found.getEmail()).isEqualTo(email);
        assertThat(found.getName()).isEqualTo(customer.getName());
    }

    private Customer createCustomer(String name, String email) {
        return new Customer
                .Builder(name, email)
                .build();
    }
}