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
import org.springframework.transaction.annotation.Transactional;
import team.marco.voucher_management_system.domain.customer.Customer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@ActiveProfiles("prod")
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

    @Test
    @DisplayName("유저 생성 시 생성된 유저를  반환")
    void 사용자_생성_성공() {
        // 사용자 생성
        String name = "customer";
        String email = "customer@gmail.com";
        Customer customer = new Customer(name, email);

        Customer saved = customerRepository.insert(customer);

        // UUID와 생성 시간은 주어지지 않으면 자동으로 들어감
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getCreatedAt()).isNotNull();
        // 생성된 사용자 반환
        Assertions.assertThat(saved.getId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("동일한 이메일을 가진 유저는 생성할 수 없다.")
    void 사용자_생성_실패() {
        // 사용자 생성
        String name = "customer";
        String email = "customer@gmail.com";
        Customer customer = new Customer(name, email);
        customerRepository.insert(customer);

        // 이미 존재하는 이메일과 함께 생성 시도
        String name2 = "customer2";
        String sameEmail = "customer@gmail.com";
        Customer customer2 = new Customer(name2, sameEmail);

        // 에러 발생
        assertThrows(DuplicateKeyException.class,
                () -> customerRepository.insert(customer2));
    }

    @Test
    void 전체_사용자_목록_조회_성공() {
        // 사용자 생성
        Customer customer = new Customer("customer", "customer@gmail.com");
        Customer customer2 = new Customer("customer2", "customer2@gmail.com");
        customerRepository.insert(customer);
        customerRepository.insert(customer2);

        // 전체 사용자 목록 조회
        List<Customer> customers = customerRepository.findAll();

        // 저장된 2명의 사용자가 조회
        assertThat(customers).hasSize(2);
    }

    @Test
    void 사용자_아이디로_조회_성공() {
        // 사용자 생성
        String name = "customer";
        String email = "customer@gmail.com";
        Customer customer = new Customer(name, email);
        customerRepository.insert(customer);

        // 사용자 아이디로 조회
        Customer found = customerRepository.findById(customer.getId()).get();

        // 저장한 사용자와 동일한 사용자 반환
        Assertions.assertThat(found.getId()).isEqualTo(customer.getId());
    }
}