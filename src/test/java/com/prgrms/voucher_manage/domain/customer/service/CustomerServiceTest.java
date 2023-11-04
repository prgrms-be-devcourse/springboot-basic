package com.prgrms.voucher_manage.domain.customer.service;

import com.prgrms.voucher_manage.domain.customer.controller.dto.CreateCustomerReq;
import com.prgrms.voucher_manage.domain.customer.controller.dto.UpdateCustomerReq;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.repository.JdbcCustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.BLACK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private JdbcCustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @Test
    @DisplayName("생성된 회원을 반환 받을 수 있다.")
    void createCustomer(){
        Customer customer = new Customer("까마귀", BLACK);
        CreateCustomerReq customerDto = new CreateCustomerReq("까마귀", BLACK);

        when(repository.save(any(Customer.class))).thenReturn(customer);
        Customer savedCustomer = service.save(customerDto);

        assertThat(savedCustomer).isNotNull();
    }

    @Test
    @DisplayName("회원 아이디로 회원을 반환받을 수 있다.")
    void findCustomerById(){
        Customer customer = new Customer("까마귀", BLACK);
        CreateCustomerReq customerDto = new CreateCustomerReq("까마귀", BLACK);

        when(repository.save(any(Customer.class))).thenReturn(customer);
        Customer savedCustomer = service.save(customerDto);

        when(repository.findById(any())).thenReturn(savedCustomer);

        assertThat(service.findById(savedCustomer.getId()).getId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("회원 이름으로 회원을 반환 받을 수 있다.")
    void findCustomerByName(){
        Customer customer = new Customer("까마귀", BLACK);
        CreateCustomerReq customerDto = new CreateCustomerReq("까마귀", BLACK);

        when(repository.save(any(Customer.class))).thenReturn(customer);
        Customer savedCustomer = service.save(customerDto);

        when(repository.findByName(any())).thenReturn(savedCustomer);
        assertThat(service.findByName(savedCustomer.getName()).getName()).isEqualTo(customer.getName());
    }

    @Test
    @DisplayName("회원 이름을 변경할 수 있다.")
    void update() {
        Customer customer = new Customer("까마귀", BLACK);
        UpdateCustomerReq dto = new UpdateCustomerReq("갈매기");

        when(repository.findById(any())).thenReturn(new Customer(dto.name(), BLACK));

        service.update(customer.getId(), dto);

        Customer updatedCustomer = service.findById(customer.getId());

        assertThat(updatedCustomer.getName()).isEqualTo(dto.name());
    }

    @Test
    @DisplayName("블랙 회원 리스트를 조회할 수 있다.")
    void getBlackCustomers(){
        Customer customer1 = new Customer("까마귀", BLACK);
        Customer customer2 = new Customer("갈매기", BLACK);

        when(repository.findByType(BLACK.getData())).thenReturn(List.of(customer1, customer2));

        List<Customer> blackCustomers = service.getBlackCustomers();

        assertThat(blackCustomers.size()).isEqualTo(2);
    }
}
