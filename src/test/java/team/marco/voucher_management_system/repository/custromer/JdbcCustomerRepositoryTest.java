package team.marco.voucher_management_system.repository.custromer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import team.marco.voucher_management_system.domain.customer.Customer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class JdbcCustomerRepositoryTest {
    @Autowired
    private JdbcCustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        String query = "DELETE FROM customers";
        jdbcTemplate.update(query);
    }

    @DisplayName("사용자를 생성할 수 있습니다.")
    @Test
    void insert() {
        // given
        String name = "customer";
        String email = "customer@gmail.com";
        Customer customer = createCustomer(name, email);

        // when
        Customer saved = customerRepository.insert(customer);

        // then
        // UUID와 생성 시간은 주어지지 않으면 자동으로 들어감
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getCreatedAt()).isNotNull();
        Assertions.assertThat(saved.getId()).isEqualTo(customer.getId());
    }

    @DisplayName("동일한 이메일을 가진 유저는 생성할 수 없습니다.")
    @Test
    void insertWithDuplicateEmail() {
        // given
        String name = "customer";
        String email = "customer@gmail.com";
        Customer customer = createCustomer(name, email);
        customerRepository.insert(customer);

        String name2 = "customer2";
        String sameEmail = "customer@gmail.com";
        Customer wrongCustomer = createCustomer(name2, sameEmail);

        // when then
        assertThrows(DuplicateKeyException.class,
                () -> customerRepository.insert(wrongCustomer));
    }

    @DisplayName("전체 사용자 목록을 조회할 수 있습니다.")
    @Test
    void findAll() {
        // given
        Customer customer = createCustomer("customer", "customer@gmail.com");
        Customer customer2 = createCustomer("customer2", "customer2@gmail.com");
        customerRepository.insert(customer);
        customerRepository.insert(customer2);

        // when
        List<Customer> customers = customerRepository.findAll();

        // then
        assertThat(customers).hasSize(2);
    }

    @DisplayName("사용자 아이디로 사용자를 조회할 수 있습니다.")
    @Test
    void findById() {
        // given
        String name = "customer";
        String email = "customer@gmail.com";
        Customer customer = createCustomer(name, email);
        customerRepository.insert(customer);

        // when
        Customer found = customerRepository.findById(customer.getId()).get();

        // then
        Assertions.assertThat(found.getId()).isEqualTo(customer.getId());
    }

    @DisplayName("사용자 이메일로 사용자를 조회할 수 있다.")
    @Test
    void findByEmail() {
        // given
        String name = "customer";
        String email = "customer@gmail.com";
        Customer customer = createCustomer(name, email);
        customerRepository.insert(customer);

        // when
        Customer found = customerRepository.findByEmail("customer@gmail.com").get();

        // then
        Assertions.assertThat(found.getId()).isEqualTo(customer.getId());
    }

    private Customer createCustomer(String name, String email) {
        return new Customer
                .Builder(name, email)
                .build();
    }
}