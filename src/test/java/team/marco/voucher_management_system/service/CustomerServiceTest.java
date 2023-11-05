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

    @Test
    void 사용자_ID로_조회() {
        // 사용자 등록
        Customer customer = createCustomer("test", "test@gmail.com");
        customerRepository.insert(customer);

        // 사용자 조회
        Customer found = customerService.findCustomerById(customer.getId());

        // 등록된 사용자와 동일한 사용자가 조회됨
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