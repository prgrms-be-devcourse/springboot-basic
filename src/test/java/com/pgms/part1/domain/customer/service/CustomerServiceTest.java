package com.pgms.part1.domain.customer.service;

import com.pgms.part1.domain.customer.dto.CustomerCreateRequestDto;
import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
//@Sql("/truncate.sql")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void customer_생성_성공() {
        // given
        CustomerCreateRequestDto customerCreateRequestDto = new CustomerCreateRequestDto("test", "test");

        // when
        Customer customer = customerService.addCustomer(customerCreateRequestDto);

        // then
        Assertions.assertEquals(customerCreateRequestDto.name(), customer.getName());
    }

    @Test
    void customer_블랙리스트_등록_성공() {
        // given
        CustomerCreateRequestDto customerCreateRequestDto = new CustomerCreateRequestDto("test", "test");
        Customer customer = customerService.addCustomer(customerCreateRequestDto);

        // when
        customerService.updateCustomerBlocked(customer.getId());

        // then
        List<CustomerResponseDto> blacklist = customerService.listBlockedCustomers();
        assertThat(blacklist).extracting("id").contains(customer.getId());
    }

    @Test
    void customer_삭제_성공(){
        // given
        CustomerCreateRequestDto customerCreateRequestDto = new CustomerCreateRequestDto("test", "test");
        Customer customer = customerService.addCustomer(customerCreateRequestDto);

        // when
        customerService.deleteCustomer(customer.getId());

        // then
        List<CustomerResponseDto> customerResponseDtos = customerService.listCustomers();
        assertThat(customerResponseDtos.isEmpty());
    }

    @Test
    void customer_전체_리스트_조회_성공(){
        // given
        CustomerCreateRequestDto customerCreateRequestDto1 = new CustomerCreateRequestDto("test", "test1");
        Customer customer1 = customerService.addCustomer(customerCreateRequestDto1);
        CustomerCreateRequestDto customerCreateRequestDto2 = new CustomerCreateRequestDto("test", "test2");
        Customer customer2 = customerService.addCustomer(customerCreateRequestDto2);
        CustomerCreateRequestDto customerCreateRequestDto3 = new CustomerCreateRequestDto("test", "test3");
        Customer customer3 = customerService.addCustomer(customerCreateRequestDto3);

        // when
        List<CustomerResponseDto> customerResponseDtos = customerService.listCustomers();

        // then
        Assertions.assertEquals(3, customerResponseDtos.size());
    }
}
