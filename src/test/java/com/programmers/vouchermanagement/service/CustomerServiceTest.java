package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.CustomerDto;
import com.programmers.vouchermanagement.message.ErrorMessage;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    private final List<Customer> testCustomers = new ArrayList<>();
    private final Customer customer1 = new Customer(new CustomerDto.CreateRequest("user1"));
    private final Customer customer2 = new Customer(new CustomerDto.CreateRequest("user2"));
    private final Customer customer3 = new Customer(new CustomerDto.CreateRequest("user3"));
    private final Customer customer4 = new Customer(new CustomerDto.CreateRequest("user4"));

    @Test
    void 모든_고객을_가져올_수_있다() {
        //given
        testCustomers.add(customer1);
        testCustomers.add(customer2);
        testCustomers.add(customer3);
        testCustomers.add(customer4);
        doReturn(testCustomers).when(customerRepository).findAll();

        //when
        final List<Customer> allCustomers = customerService.findAllCustomers();

        //then
        assertThat(allCustomers.size()).isEqualTo(testCustomers.size());
    }

    @Test
    void ID로_고객을_가져올_수_있다() {
        //given
        doReturn(Optional.ofNullable(customer1)).when(customerRepository).findById(customer1.getId());

        //when
        final Customer customer = customerService.findCustomerById(customer1.getId());

        //then
        assertThat(customer).isEqualTo(customer1);
    }

    @Test
    void 이름에_특정문자열이_포함된_고객을_가져올_수_있다() {
        //given
        final List<Customer> singletonCustomers = Collections.singletonList(customer2);
        doReturn(singletonCustomers).when(customerRepository).findByNameLike("2");

        //when
        final List<Customer> customers = customerService.findCustomerByName("2");

        //then
        assertThat(customers).hasSameSizeAs(singletonCustomers).contains(customer2);
    }

    @Test
    void 원하는_이름으로_고객을_생성할_수_있다() {
        //given
        String name = "customName";
        CustomerDto.CreateRequest customerDto = new CustomerDto.CreateRequest(name);
        Customer customer = new Customer(customerDto);
        doReturn(customer).when(customerRepository).save(any(Customer.class));
        doReturn(Optional.empty()).when(customerRepository).findByName(name);

        //when
        final Customer savedCustomer = customerService.createCustomer(customerDto);

        //then
        assertThat(savedCustomer).isEqualTo(customer);
    }

    @Test
    void 고객의_이름은_중복될_수_없다() {
        //given
        String name = "customName";
        CustomerDto.CreateRequest customerDto = new CustomerDto.CreateRequest(name);
        Customer customer = new Customer(customerDto);
        doReturn(Optional.ofNullable(customer)).when(customerRepository).findByName(name);

        //when&then
        assertThatThrownBy(() -> customerService.createCustomer(customerDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.CUSTOMER_ALREADY_EXISTS_MESSAGE.getMessage());
    }

    @Test
    void 존재하지_않는_고객을_삭제할_수_없다() {
        //given
        doReturn(Optional.empty()).when(customerRepository).findById(any(UUID.class));

        //when&then
        assertThatThrownBy(() -> customerService.deleteCustomer(UUID.randomUUID()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining(ErrorMessage.CUSTOMER_NOT_FOUND_MESSAGE.getMessage());
    }
}
