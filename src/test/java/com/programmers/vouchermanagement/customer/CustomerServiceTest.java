package com.programmers.vouchermanagement.customer;

import com.programmers.vouchermanagement.customer.application.CustomerService;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    @DisplayName("블랙리스트 고객 명단을 조회할 수 있다.")
    void successReadAllBlackList() {

        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "김뫄뫄", CustomerType.BLACK);
        Customer customer2 = new Customer(UUID.randomUUID(), "이롸롸", CustomerType.BLACK);
        Customer customer3 = new Customer(UUID.randomUUID(), "최뭐뭐", CustomerType.BLACK);
        List<Customer> customers = new ArrayList<>() {{
            add(customer1);
            add(customer2);
            add(customer3);
        }};
        when(customerRepository.findAllBlack()).thenReturn(customers);

        // when
        List<CustomerResponseDto> customerResponseDtos = customerService.readAllBlackList();

        // then
        assertThat(customerResponseDtos).hasSize(3)
                .extracting(CustomerResponseDto::getCustomerId)
                .contains(customer1.getCustomerId(), customer2.getCustomerId(), customer3.getCustomerId());
    }
}
