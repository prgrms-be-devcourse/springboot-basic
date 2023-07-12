package com.programmers.springweekly.service.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.dto.customer.request.CustomerCreateRequest;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.dto.customer.response.CustomerResponse;
import com.programmers.springweekly.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    void save() {
        // given
        CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateRequest);

        // when
        CustomerListResponse customerListAfter = customerService.findAll();

        // then
        assertThat(customerListAfter.getCustomerList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("고객의 정보를 업데이트 할 수 있다.")
    void update() {
        // given
        CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateRequest);
        CustomerListResponse customerList = customerService.findAll();
        CustomerResponse beforeCustomer = customerList.getCustomerList().get(0);
        CustomerUpdateRequest customerUpdateRequest = CustomerUpdateRequest.builder()
                .customerId(beforeCustomer.getCustomerId())
                .customerName("changhyeonh")
                .customerEmail("changhyeon.h1@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        // when
        customerService.update(customerUpdateRequest);
        customerList = customerService.findAll();
        CustomerResponse afterCustomer = customerList.getCustomerList().get(0);

        // then
        assertThat(beforeCustomer).usingRecursiveComparison().isNotEqualTo(afterCustomer);
    }

    @Test
    @DisplayName("모든 고객들을 조죄할 수 있다.")
    void finaAll() {
        // given
        CustomerCreateRequest customerCreateRequest1 = CustomerCreateRequest.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        CustomerCreateRequest customerCreateRequest2 = CustomerCreateRequest.builder()
                .customerName("dong")
                .customerEmail("dong.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        CustomerCreateRequest customerCreateRequest3 = CustomerCreateRequest.builder()
                .customerName("yang")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateRequest1);
        customerService.save(customerCreateRequest2);
        customerService.save(customerCreateRequest3);

        // when
        CustomerListResponse customerList = customerService.findAll();

        // then
        assertThat(customerList.getCustomerList().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("고객 타입이 블랙리스트인 고객들을 조죄할 수 있다.")
    void getBlackList() {
        // given
        CustomerCreateRequest customerCreateRequest1 = CustomerCreateRequest.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.NORMAL)
                .build();

        CustomerCreateRequest customerCreateRequest2 = CustomerCreateRequest.builder()
                .customerName("dong")
                .customerEmail("dong.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        CustomerCreateRequest customerCreateRequest3 = CustomerCreateRequest.builder()
                .customerName("yang")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateRequest1);
        customerService.save(customerCreateRequest2);
        customerService.save(customerCreateRequest3);

        // when
        CustomerListResponse customerList = customerService.getBlackList();

        // then
        assertThat(customerList.getCustomerList().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("고객 아이디로 고객을 삭제할 수 있다.")
    void deleteById() {
        // given
        CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateRequest);

        CustomerListResponse customerListBefore = customerService.findAll();

        // when
        customerService.deleteById(customerListBefore.getCustomerList().get(0).getCustomerId());
        CustomerListResponse customerListAfter = customerService.findAll();

        // then
        assertThat(customerListAfter.getCustomerList().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("모든 고객을 삭제할 수 있다.")
    void deleteAll() {
        // given
        CustomerCreateRequest customerCreateRequest1 = CustomerCreateRequest.builder()
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.NORMAL)
                .build();

        CustomerCreateRequest customerCreateRequest2 = CustomerCreateRequest.builder()
                .customerName("dong")
                .customerEmail("dong.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        CustomerCreateRequest customerCreateRequest3 = CustomerCreateRequest.builder()
                .customerName("yang")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        customerService.save(customerCreateRequest1);
        customerService.save(customerCreateRequest2);
        customerService.save(customerCreateRequest3);

        // when
        customerService.deleteAll();
        CustomerListResponse customerListAfter = customerService.findAll();

        // then
        assertThat(customerListAfter.getCustomerList().size()).isEqualTo(0);
    }

}
