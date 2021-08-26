package org.programmers.kdt.customer.service;

import org.junit.jupiter.api.*;
import org.programmers.kdt.AppConfiguration;
import org.programmers.kdt.customer.Customer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    CustomerService customerService;

    private List<Customer> testCustomers = new ArrayList<>();

    @BeforeAll
    void setUp() {
        context.register(AppConfiguration.class);
        ConfigurableEnvironment environment = context.getEnvironment();
        // environment.setActiveProfiles("test");
        environment.setActiveProfiles("dev");
        context.refresh();
        customerService = context.getBean(CustomerService.class);
    }

    @AfterAll
    void tearDown() {
        for (Customer customer : testCustomers) {
            customerService.removeCustomer(customer);
        }
    }

    @Test
    @Order(1)
    @DisplayName("회원 가입")
    void signUp() {
        for (int i = 0; i < 100; i++) {
            Customer customer = customerService.signUp(UUID.randomUUID(), "Tester-" + i, "Tester-" + i + "@gmail.com");
            testCustomers.add(customer);
            assertThat(customer).isNotNull();
        }
    }

    @Test
    @Order(2)
    @DisplayName("ID로 회원 찾기")
    void findCustomerById() {
        // 성공
        for (Customer customer : testCustomers) {
            assertThat(customerService.findCustomerById(customer.getCustomerId())).isPresent();
        }

        // 실패
        assertThat(customerService.findCustomerById(UUID.randomUUID())).isNotPresent();
    }

    @Test
    @Order(3)
    @DisplayName("이름으로 회원 찾기")
    void findCustomersByName() {
        // 성공
        for (Customer customer : testCustomers) {
            assertThat(customerService.findCustomersByName(customer.getName())).contains(customer);
        }

        // 실패
        assertThat(customerService.findCustomersByName("lorem ipsum")).isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("email로 회원 찾기")
    void findCustomerByEmail() {
        // 성공
        for (Customer customer : testCustomers) {
            assertThat(customerService.findCustomerByEmail(customer.getEmail())).isPresent();
        }

        // 실패
        assertThat(customerService.findCustomerByEmail("com.gmail@dsaharhaesz")).isNotPresent();
    }

    @Test
    @Order(5)
    @DisplayName("모든 회원 조회하기")
    void findAll() {
        List<Customer> allCustomers = customerService.findAll();
        for (Customer customer : testCustomers) {
            assertThat(customer).isIn(allCustomers);
        }
    }

    @Test
    @Order(6)
    @DisplayName("블랙리스트에 추가하기")
    void addToBlacklist() {
        for (Customer customer : testCustomers) {
            assertThat(customerService.addToBlacklist(customer)).isNotNull();
        }
    }


    @Test
    @Order(7)
    @DisplayName("블랙리스트에 등록된 모든 회원 조회하기")
    void findAllBlacklistCustomer() {
        List<Customer> allCustomers = customerService.findAll();
        for (Customer customer : testCustomers) {
            assertThat(customer).isIn(allCustomers);
        }
    }

    @Test
    @Order(8)
    @DisplayName("블랙리스트 멤버십 테스트")
    void isBlacklisted() {
        for (Customer customer : testCustomers) {
            assertThat(customerService.isBlacklisted(customer)).isTrue();
        }
    }

    @Test
    @Order(9)
    @DisplayName("회원 탈퇴(삭제)")
    void removeCustomer() {
        for (Customer customer : testCustomers) {
            assertThat(customerService.removeCustomer(customer)).isPresent();
        }
    }

}