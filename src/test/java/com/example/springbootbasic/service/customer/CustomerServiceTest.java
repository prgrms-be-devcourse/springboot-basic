package com.example.springbootbasic.service.customer;

import com.example.springbootbasic.config.TestConfig;
import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.repository.customer.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.example.springbootbasic.domain.customer.CustomerStatus.BLACK;
import static com.example.springbootbasic.domain.customer.CustomerStatus.NORMAL;
import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
class CustomerServiceTest extends TestConfig {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
        this.customerService = new CustomerService(customerRepository);
    }

    @Test
    @DisplayName("블랙리스트로 등록된 모든 고객 검색에 성공한다.")
    void whenFindBlackCustomersThenSuccessTest() {
        // given
        List<Customer> allBlackCustomers = customerService.findCustomersByStatus(BLACK);

        // when
        boolean isAllBlackCustomers = allBlackCustomers.stream().allMatch(Customer::isBlack);

        // then
        assertThat(isAllBlackCustomers).isTrue();
    }

    @Test
    @DisplayName("일반 타입으로 등록된 모든 고객 검색에 성공한다.")
    void whenFindNormalCustomersThenSuccessTest() {
        // given
        List<Customer> allNormalCustomers = customerService.findCustomersByStatus(NORMAL);

        // when
        boolean isAllNormalCustomers = allNormalCustomers.stream().allMatch(Customer::isNormal);

        // then
        assertThat(isAllNormalCustomers).isTrue();
    }

    @Test
    @DisplayName("타입으로 검색 시 null이 들어갔을 경우 Collections.emptyList()를 반환한다.")
    void whenFindCustomersByNullThenSuccessTest() {
        // when
        List<Customer> allCustomers = customerService.findCustomersByStatus(null);

        // then
        assertThat(allCustomers).isEqualTo(EMPTY_LIST);
    }

    @Test
    @DisplayName("모든 타입 고객 검색에 성공한다.")
    void whenFindAllCustomersThenSuccessTest() {
        // when
        List<Customer> allCustomers = customerService.findAllCustomers();

        // then
        assertThat(allCustomers).isNotNull();
    }

    @Test
    @DisplayName("모든 고객을 검색했을 때 고객 테이블이 비어있을 경우 Collections.EmptyList()를 반환한다.")
    void whenEmptyCustomersThenSuccessTest() {
        // when
        List<Customer> allCustomers = customerService.findAllCustomers();

        // then
        assertThat(allCustomers).isEqualTo(EMPTY_LIST);
    }
}