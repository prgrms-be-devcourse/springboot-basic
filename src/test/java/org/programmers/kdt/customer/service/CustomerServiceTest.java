package org.programmers.kdt.customer.service;

import org.junit.jupiter.api.*;
import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.customer.repository.CustomerRepository;
import org.programmers.kdt.customer.repository.FileCustomerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
    CustomerService customerService;
    CustomerRepository customerRepository;

    @BeforeAll
    void setUp() {
        customerRepository = mock(FileCustomerRepository.class);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    @Order(1)
    @DisplayName("회원 가입")
    void signUp() {
        Customer customer = customerService.signUp(UUID.randomUUID(), "Test", "Test@gmail.com");
        when(customerRepository.insert(customer)).thenReturn(customer);
        assertThat(customerService.signUp(customer.getCustomerId(), customer.getName(), customer.getEmail())).isEqualTo(customer);
    }

    @Test
    @Order(2)
    @DisplayName("ID로 회원 찾기")
    void findCustomerById() {
        Customer customer = new Customer(UUID.randomUUID(), "Test", "Test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        // 성공
        assertThat(customerService.findCustomerById(customer.getCustomerId())).isEqualTo(Optional.of(customer));

        // 실패
        assertThat(customerService.findCustomerById(UUID.randomUUID())).isEmpty();
    }

    @Test
    @Order(3)
    @DisplayName("이름으로 회원 찾기")
    void findCustomersByName() {
        Customer customer = new Customer(UUID.randomUUID(), "Test", "Test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        when(customerRepository.findByName(customer.getName())).thenReturn(List.of(customer));
        // 성공
        assertThat(customerService.findCustomersByName(customer.getName())).contains(customer);

        // 실패
        assertThat(customerService.findCustomersByName("lorem ipsum")).isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("email로 회원 찾기")
    void findCustomerByEmail() {
        Customer customer = new Customer(UUID.randomUUID(), "Test", "Test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        // 성공
        assertThat(customerService.findCustomerByEmail(customer.getEmail())).isPresent();

        // 실패
        assertThat(customerService.findCustomerByEmail("com.gmail@dsaharhaesz")).isEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("모든 회원 조회하기")
    void findAll() {
        Customer customer = new Customer(UUID.randomUUID(), "Test", "Test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "Test2", "Test2@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Customer customer3 = new Customer(UUID.randomUUID(), "Test3", "Test3@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        when(customerRepository.findAll()).thenReturn(List.of(customer, customer2, customer3));

        assertThat(customerService.findAll()).contains(customer);
        assertThat(customerService.findAll()).contains(customer2);
        assertThat(customerService.findAll()).contains(customer3);

        Customer customer4 = new Customer(UUID.randomUUID(), "Test4", "Test4@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        assertThat(customerService.findAll()).doesNotContain(customer4);
    }

    @Test
    @Order(6)
    @DisplayName("블랙리스트에 추가하기")
    void addToBlacklist() {
        Customer customer = new Customer(UUID.randomUUID(), "Test1", "Test1@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        when(customerRepository.registerToBlacklist(customer)).thenReturn(customer);

        assertThat(customerService.addToBlacklist(customer)).isEqualTo(customer);
    }


    @Test
    @Order(7)
    @DisplayName("블랙리스트에 등록된 모든 회원 조회하기")
    void findAllBlacklistCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "Test", "Test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "Test2", "Test2@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Customer customer3 = new Customer(UUID.randomUUID(), "Test3", "Test3@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        when(customerRepository.findAllBlacklistCustomer()).thenReturn(List.of(customer, customer2, customer3));

        assertThat(customerService.findAllBlacklistCustomer()).contains(customer);
        assertThat(customerService.findAllBlacklistCustomer()).contains(customer2);
        assertThat(customerService.findAllBlacklistCustomer()).contains(customer3);

        Customer customer4 = new Customer(UUID.randomUUID(), "Test4", "Test4@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        assertThat(customerService.findAllBlacklistCustomer()).doesNotContain(customer4);
    }

    @Test
    @Order(8)
    @DisplayName("블랙리스트 멤버십 테스트")
    void isBlacklisted() {
        Customer customer = new Customer(UUID.randomUUID(), "Test", "Test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "Test2", "Test2@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        when(customerRepository.findAllBlacklistCustomer()).thenReturn(List.of(customer, customer2));

        assertThat(customerService.isOnBlacklist(customer)).isTrue();
        assertThat(customerService.isOnBlacklist(customer2)).isTrue();

        Customer customer3 = new Customer(UUID.randomUUID(), "Test3", "Test3@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        assertThat(customerService.isOnBlacklist(customer3)).isFalse();
    }

    @Test
    @Order(9)
    @DisplayName("회원 탈퇴(삭제)")
    void removeCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "Test", "Test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        doNothing().when(customerRepository).deleteCustomer(customer.getCustomerId());

        customerService.removeCustomer(customer.getCustomerId());
        verify(customerRepository, times(1)).deleteCustomer(customer.getCustomerId());

    }

}