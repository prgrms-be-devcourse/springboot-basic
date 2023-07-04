package com.programmers.springweekly.service.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import com.programmers.springweekly.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    @DisplayName("블랙리스트인 고객을 모두 조회한다.")
    void getBlackList() {
        // given
        UUID customer1UUID = UUID.fromString("1927f6b6-55bf-4f1f-d6e2-043198833df7");
        UUID customer2UUID = UUID.fromString("80f4ef40-1a44-4ef2-9c8d-714ff0c8353e");
        UUID customer3UUID = UUID.fromString("202e65fe-bdd2-4f0b-db43-307d5c24ad4a");

        List<Customer> expectBlacklist = List.of(
                new Customer(customer1UUID, "changhyeon", "changhyeon.h@kakao.com", CustomerType.BLACKLIST),
                new Customer(customer2UUID, "changhyeon1", "changhyeon.h@kakao.com", CustomerType.BLACKLIST),
                new Customer(customer3UUID, "changhyeon2", "changhyeon.h@kakao.com", CustomerType.BLACKLIST)
        );

        // when
        given(customerRepository.getBlackList()).willReturn(expectBlacklist);
        List<Customer> actualCustomer = customerService.getBlackList();

        // then
        assertThat(actualCustomer).isEqualTo(expectBlacklist);
        then(customerRepository).should().getBlackList();
    }
}
