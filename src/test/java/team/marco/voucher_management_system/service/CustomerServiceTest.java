package team.marco.voucher_management_system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import team.marco.voucher_management_system.domain.customer.Customer;
import team.marco.voucher_management_system.repository.custromer.CustomerRepository;
import team.marco.voucher_management_system.service.customer.CustomerService;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
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
        Customer customer = new Customer("test", "test@gmail.com");
        customerRepository.insert(customer);

        // 사용자 조회
        Customer found = customerService.findCustomer(customer.getId());

        // 등록된 사용자와 동일한 사용자가 조회됨
        assertThat(found.getId()).isEqualTo(customer.getId());
        assertThat(found.getName()).isEqualTo(customer.getName());
        assertThat(found.getEmail()).isEqualTo(customer.getEmail());
    }
}