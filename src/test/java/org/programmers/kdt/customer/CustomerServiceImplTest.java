package org.programmers.kdt.customer;

import org.junit.jupiter.api.*;
import org.programmers.kdt.AppConfiguration;
import org.programmers.kdt.customer.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceImplTest {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
    CustomerService customerService = applicationContext.getBean(CustomerService.class);

    private List<Customer> customers;
    private List<Customer> blacklistCustomers;

    @BeforeAll
    void setupTestData() {
        customers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UUID uuid = UUID.randomUUID();
            String name = "Customer--" + i;
            String email = "EHEDSsdhshPPP00" + i + "@google.com";

            Customer customer = new Customer(uuid, name, email);
            customers.add(customer);
        }
    }

    @BeforeEach
    void ready() {
        for (Customer customer : customers) {
            customerService.join(customer.getCustomerId(), customer.getName(), customer.getEmail());
        }
        blacklistCustomers = new ArrayList<>();
    }

    @Test
    @DisplayName("회원가입")
    void join() {
        for (Customer customer : customers) {
            assertThat(customerService.join(customer.getCustomerId(), customer.getName(), customer.getEmail()))
                    .isEqualTo(customer);
        }
    }

    @Test
    @DisplayName("ID로 회원 찾기")
    void findCustomerById() {
        for (Customer customer : customers) {
            assertThat(customerService.findCustomerById(customer.getCustomerId())).isEqualTo(customer);
        }
    }

    @Test
    @DisplayName("이름으로 회원 찾기")
    void findCustomerByName() {
        for (Customer customer : customers) {
            List<Customer> customerList = customerService.findCustomersByName(customer.getName());
            assertThat(customerList).contains(customer);
        }
    }

    @Test
    @DisplayName("이메일로 회원 찾기")
    void findCustomersByEmail() {
        for (Customer customer : customers) {
            assertThat(customerService.findCustomersByEmail(customer.getEmail())).contains(customer);
        }
    }

    @Test
    @DisplayName("블랙리스트에 등록하기")
    void registerToBlacklist() {
        for (int i = 0; i < 2; i++) {
            if (!blacklistCustomers.contains(customers.get(i))) {
                blacklistCustomers.add(customers.get(i));
                assertThat(customerService.registerToBlacklist(customers.get(i))).isEqualTo(customers.get(i));
            }
        }
    }

    @Test
    @DisplayName("블랙리스트 회원 불러오기")
    void getBlacklistCustomers() {
        // 블랙리스트로 등록할 회원 선별
        for (int i = 0; i < 3; i++) {
            blacklistCustomers.add(customers.get(i));
        }
        // 블랙리스트 등록
        for (Customer customer : blacklistCustomers) {
            customerService.registerToBlacklist(customer);
        }
        // 확인
        List<Customer> blacklist = customerService.getBlacklistCustomers();
        for (Customer customer : blacklistCustomers) {
            assertThat(blacklist).contains(customer);
        }
    }

    @Test
    @DisplayName("모든 회원 불러오기")
    void getAllCustomer() {
        List<Customer> allCustomer = customerService.getAllCustomer();
        for (Customer customer : customers) {
            assertThat(allCustomer).contains(customer);
        }
    }

    @Test
    @DisplayName("블랙리스트 멤버십 테스트")
    void isOnBlacklist() {
        // 블랙리스트 등록할 멤버 선정
        for (int i = 0; i < 2; i++) {
            blacklistCustomers.add(customers.get(i));
        }
        // 블랙리스트 등록
        for (Customer customer : blacklistCustomers) {
            customerService.registerToBlacklist(customer);
        }
        // 확인
        for (int i = 0; i < 2; i++) {
            assertThat(customerService.isOnBlacklist(customers.get(i))).isTrue();
        }
        for (int i = 2; i < 5; i++) {
            assertThat(customerService.isOnBlacklist(customers.get(i))).isFalse();
        }
    }
}