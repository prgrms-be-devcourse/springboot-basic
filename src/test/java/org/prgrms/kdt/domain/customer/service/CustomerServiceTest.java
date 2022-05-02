package org.prgrms.kdt.domain.customer.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.customer.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    @DisplayName("블랙리스트 고객들을 조회할 수 있다.")
    void getBlackCustomers(){
        //given
        LocalDateTime now = LocalDateTime.now();
        Customer customerPark = new Customer(UUID.randomUUID(), "park", "a@naver.com", CustomerType.BLACK_LIST, now, now);
        Customer customerKim = new Customer(UUID.randomUUID(),"kim" , "b@gmail.com", CustomerType.BLACK_LIST, now, now);
        List<Customer> savedBlackCustomers = Arrays.asList(customerPark, customerKim);
        //when
        when(customerRepository.findAllByType(any())).thenReturn(savedBlackCustomers);
        List<Customer> blackListCustomers = customerService.getBlackCustomers();
        //then
        assertThat(blackListCustomers).contains(customerKim, customerPark);
    }

    @Test
    @DisplayName("저장된 고객목록을 조회할 수 있다.")
    void getAllCustomers() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        Customer customerPark = new Customer(UUID.randomUUID(), "park", "a@naver.com", CustomerType.NORMAL, now, now);
        Customer customerKim = new Customer(UUID.randomUUID(),"kim" , "b@gmail.com", CustomerType.BLACK_LIST, now, now);
        List<Customer> savedCustomers = Arrays.asList(customerPark, customerKim);
        //when
        when(customerRepository.findAll()).thenReturn(savedCustomers);
        List<Customer> customers = customerService.getAllCustomers();
        //then
        assertThat(customers).contains(customerKim, customerPark);
    }

    @Test
    @DisplayName("입력받은 값을 통해 고객을 저장할 수 있다.")
    void createCustomer() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new Customer(customerId,
                "park",
                "a@gmail.com",
                CustomerType.NORMAL,
                now,
                now);
        //when
        when(customerRepository.save(any())).thenReturn(customerId);
        UUID savedId = customerService.save(customer);
        //then
        assertThat(savedId).isEqualTo(customerId);

    }


}