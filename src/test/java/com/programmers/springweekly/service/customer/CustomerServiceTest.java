package com.programmers.springweekly.service.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.dto.CustomerCreateDto;
import com.programmers.springweekly.dto.CustomerUpdateDto;
import com.programmers.springweekly.service.CustomerService;
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
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void save() {
        // given
        CustomerCreateDto customerCreateDto = CustomerCreateDto.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateDto);

        // when
        List<Customer> customerListAfter = customerService.findAll();

        // then
        assertThat(customerListAfter.size()).isEqualTo(1);
    }

    @Test
    void update() {
        // given
        CustomerCreateDto customerCreateDto = CustomerCreateDto.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateDto);
        List<Customer> customerList = customerService.findAll();
        Customer beforeCustomer = customerList.get(0);
        CustomerUpdateDto customerUpdateDto = CustomerUpdateDto.builder()
                .customerId(beforeCustomer.getCustomerId())
                .customerName("changhyeonh")
                .customerEmail("changhyeon.h1@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        // when
        customerService.update(customerUpdateDto);
        customerList = customerService.findAll();
        Customer afterCustomer = customerList.get(0);

        // then
        assertThat(beforeCustomer).usingRecursiveComparison().isNotEqualTo(afterCustomer);
    }

    @Test
    void finaAll() {
        // given
        CustomerCreateDto customerCreateDto1 = CustomerCreateDto.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        CustomerCreateDto customerCreateDto2 = CustomerCreateDto.builder()
                .customerName("dong")
                .customerEmail("dong.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        CustomerCreateDto customerCreateDto3 = CustomerCreateDto.builder()
                .customerName("yang")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateDto1);
        customerService.save(customerCreateDto2);
        customerService.save(customerCreateDto3);

        // when
        List<Customer> customerList = customerService.findAll();

        // then
        assertThat(customerList.size()).isEqualTo(3);
    }

    @Test
    void getBlackList() {
        // given
        CustomerCreateDto customerCreateDto1 = CustomerCreateDto.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.NORMAL)
                .build();

        CustomerCreateDto customerCreateDto2 = CustomerCreateDto.builder()
                .customerName("dong")
                .customerEmail("dong.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        CustomerCreateDto customerCreateDto3 = CustomerCreateDto.builder()
                .customerName("yang")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateDto1);
        customerService.save(customerCreateDto2);
        customerService.save(customerCreateDto3);

        // when
        List<Customer> customerList = customerService.getBlackList();

        // then
        assertThat(customerList.size()).isEqualTo(2);
    }

    @Test
    void deleteById() {
        // given
        CustomerCreateDto customerCreateDto = CustomerCreateDto.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        List<Customer> customerListBefore = customerService.findAll();

        // when
        customerService.deleteById(customerListBefore.get(0).getCustomerId());
        List<Customer> customerListAfter = customerService.findAll();

        // then
        assertThat(customerListAfter.size()).isEqualTo(0);
    }

    @Test
    void deleteAll() {
        // given
        CustomerCreateDto customerCreateDto1 = CustomerCreateDto.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.NORMAL)
                .build();

        CustomerCreateDto customerCreateDto2 = CustomerCreateDto.builder()
                .customerName("dong")
                .customerEmail("dong.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        CustomerCreateDto customerCreateDto3 = CustomerCreateDto.builder()
                .customerName("yang")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateDto1);
        customerService.save(customerCreateDto2);
        customerService.save(customerCreateDto3);

        // when
        customerService.deleteAll();
        List<Customer> customerListAfter = customerService.findAll();

        // then
        assertThat(customerListAfter.size()).isEqualTo(0);
    }
}
