package org.prgrms.vouchermanager.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.prgrms.vouchermanager.repository.customer.MemoryCustomerRepository;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.testdata.CustomerData;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @Mock
    private MemoryCustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @Test
    @DisplayName("고객을 등록한다")
    void createCustomer() throws InstantiationException, IllegalAccessException {
        Customer customer = CustomerData.getCustomer();
        when(repository.save(any(Customer.class))).thenReturn(customer);

        Customer customer1 = service.createCustomer(CustomerData.getCustomerDto());

        assertThat(customer.getName()).isEqualTo(customer1.getName());
        verify(repository).save(any(Customer.class));
    }

    @Test
    @DisplayName("존재하는 모든 고객 정보를 조회할 수 있다")
    void findAll(){
        List<Customer> result = new ArrayList<>();
        Customer customer = CustomerData.getCustomer();
        result.add(customer);
        when(repository.findAll()).thenReturn(result);

        List<Customer> all = service.findAll();

        assertThat(all.get(0)).isEqualTo(result.get(0));
        verify(repository).findAll();

    }
}