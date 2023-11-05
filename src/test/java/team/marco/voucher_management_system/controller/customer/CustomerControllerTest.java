package team.marco.voucher_management_system.controller.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import team.marco.voucher_management_system.controller.customer.dto.CustomerIdAndName;
import team.marco.voucher_management_system.controller.customer.dto.CustomerResponse;
import team.marco.voucher_management_system.domain.customer.Customer;
import team.marco.voucher_management_system.service.customer.BlacklistService;
import team.marco.voucher_management_system.service.customer.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;

    @MockBean
    private BlacklistService blacklistService;

    private CustomerController customerController;

    @BeforeEach
    void setup() {
        this.customerController = new CustomerController(customerService, blacklistService);
    }

    @DisplayName("블랙리스트 사용자를 조회할 수 있다.")
    @Test
    void getBlacklistInfo() {
        // given
        List<CustomerIdAndName> blacklist = new ArrayList<>();

        when(blacklistService.getBlacklist()).thenReturn(blacklist);

        // when
        List<String> result = customerController.getBlacklistInfo();

        // then
        assertThat(result).isNotNull();
    }

    @DisplayName("사용자 ID로 존재하는 사용자인지 확인할 수 있다.")
    @Test
    void isExistCustomerWhenExist() {
        // given
        Customer customer = new Customer.Builder("name", "email@gmail.com").build();
        when(customerService.findCustomerById(customer.getId())).thenReturn(customer);

        // when
        boolean result = customerController.isExistCustomer(customer.getId().toString());

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("존재하지 않는 사용자가 존재하는지 요청하면  확인할 수 있다.")
    @Test
    void isExistCustomerWhenNotExist() {
        // given
        Customer customer = new Customer.Builder("name", "email@gmail.com").build();
        when(customerService.findCustomerById(customer.getId())).thenThrow(NoSuchElementException.class);

        // when
        boolean result = customerController.isExistCustomer(customer.getId().toString());

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("전체 사용자 목록을 확인할 수 있습니다.")
    @Test
    void findAll() {
        // given
        Customer customer = new Customer.Builder("name", "email@gmail.com").build();
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerService.findAllCustomer()).thenReturn(customers);

        // when
        List<CustomerResponse> result = customerController.findAll();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0))
                .extracting("name", "email")
                .containsExactly(customer.getName(), customer.getEmail());
    }

    @DisplayName("사용자 이메일로 사용자를 조회할 수 있다.")
    @Test
    void findCustomerByEmail() {
        // given
        String email = "email@gmail.com";
        Customer customer = new Customer.Builder("name", email).build();
        when(customerService.findCustomerByEmail(email)).thenReturn(customer);

        // when
        Customer found = customerController.findCustomerByEmail(email);

        // then
        assertThat(found).isNotNull();
    }
}