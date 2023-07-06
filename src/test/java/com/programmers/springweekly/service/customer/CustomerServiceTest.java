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
        CustomerCreateDto customerCreateDto = new CustomerCreateDto("changhyeon", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);
        customerService.save(customerCreateDto);

        // when
        List<Customer> customerListAfter = customerService.findAll();

        // then
        assertThat(customerListAfter.size()).isEqualTo(1);
    }

    @Test
    void update() {
        // given
        CustomerCreateDto customerCreateDto = new CustomerCreateDto("changhyeon", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);
        customerService.save(customerCreateDto);
        List<Customer> customerList = customerService.findAll();
        Customer beforeCustomer = customerList.get(0);
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto(beforeCustomer.getCustomerId(), "changhyeonh", "changhyeon.h1@kakao.com", CustomerType.BLACKLIST);

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
        CustomerCreateDto customerCreateDto1 = new CustomerCreateDto("changhyeon", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);
        CustomerCreateDto customerCreateDto2 = new CustomerCreateDto("dong", "dong@kakao.com", CustomerType.BLACKLIST);
        CustomerCreateDto customerCreateDto3 = new CustomerCreateDto("yang", "yang@kakao.com", CustomerType.BLACKLIST);

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
        CustomerCreateDto customerCreateDto1 = new CustomerCreateDto("changhyeon", "changhyeon.h@kakao.com", CustomerType.NORMAL);
        CustomerCreateDto customerCreateDto2 = new CustomerCreateDto("dong", "dong@kakao.com", CustomerType.BLACKLIST);
        CustomerCreateDto customerCreateDto3 = new CustomerCreateDto("yang", "yang@kakao.com", CustomerType.BLACKLIST);

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
        CustomerCreateDto customerCreateDto = new CustomerCreateDto("changhyeon", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);
        customerService.save(customerCreateDto);
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
        CustomerCreateDto customerCreateDto1 = new CustomerCreateDto("changhyeon", "changhyeon.h@kakao.com", CustomerType.NORMAL);
        CustomerCreateDto customerCreateDto2 = new CustomerCreateDto("dong", "dong@kakao.com", CustomerType.BLACKLIST);
        CustomerCreateDto customerCreateDto3 = new CustomerCreateDto("yang", "yang@kakao.com", CustomerType.BLACKLIST);

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
