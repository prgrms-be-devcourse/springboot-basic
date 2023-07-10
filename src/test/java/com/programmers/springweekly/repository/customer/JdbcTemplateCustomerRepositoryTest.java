package com.programmers.springweekly.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class JdbcTemplateCustomerRepositoryTest {

    @Autowired
    private JdbcTemplateCustomerRepository jdbcTemplateCustomerRepository;

    @Test
    @DisplayName("고객을 생성하여 저장한다.")
    void save() {
        // given
        Customer customerExpect = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        // when
        Customer customerActual = jdbcTemplateCustomerRepository.save(customerExpect);

        // then
        assertThat(customerActual).isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("고객을 업데이트 한다.")
    void update() {
        // given
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        CustomerUpdateRequest customerUpdateRequest = CustomerUpdateRequest.builder()
                .customerId(customer.getCustomerId())
                .customerName("hong")
                .customerEmail("hong@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        Customer customerExpect = Customer.builder()
                .customerId(customer.getCustomerId())
                .customerName(customerUpdateRequest.getCustomerName())
                .customerEmail(customerUpdateRequest.getCustomerEmail())
                .customerType(customerUpdateRequest.getCustomerType())
                .build();

        // when
        jdbcTemplateCustomerRepository.save(customer);
        jdbcTemplateCustomerRepository.update(customerExpect);
        Customer customerActual = jdbcTemplateCustomerRepository.findById(customerExpect.getCustomerId()).get();

        // then
        assertThat(customerActual).usingRecursiveComparison().isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("특정 고객을 조회한다.")
    void findById() {
        // given
        Customer customerExpect = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        // when
        jdbcTemplateCustomerRepository.save(customerExpect);

        Customer customerActual = jdbcTemplateCustomerRepository.findById(customerExpect.getCustomerId()).get();

        // then
        assertThat(customerActual).usingRecursiveComparison().isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("모든 고객을 조회한다.")
    void findAll() {
        // given
        Customer customer1 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        Customer customer2 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("hong")
                .customerEmail("hong@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        List<Customer> customerListExpect = List.of(
                customer1,
                customer2
        );

        // when
        jdbcTemplateCustomerRepository.save(customer1);
        jdbcTemplateCustomerRepository.save(customer2);

        List<Customer> customerListActual = jdbcTemplateCustomerRepository.findAll();

        // then
        assertThat(customerListActual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(customerListExpect);
    }

    @Test
    @DisplayName("고객중 타입이 블랙리스트인 고객을 조회한다.")
    void getBlackList() {
        // given
        Customer customer1 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.NORMAL)
                .build();

        Customer customer2 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("hong")
                .customerEmail("hong@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        Customer customer3 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("song")
                .customerEmail("song@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        List<Customer> customerListExpect = List.of(
                customer2,
                customer3
        );

        // when
        jdbcTemplateCustomerRepository.save(customer1);
        jdbcTemplateCustomerRepository.save(customer2);
        jdbcTemplateCustomerRepository.save(customer3);

        List<Customer> customerListActual = jdbcTemplateCustomerRepository.getBlackList();

        // then
        assertThat(customerListActual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(customerListExpect);
    }

    @Test
    @DisplayName("고객을 찾아 저장소에서 삭제한다.")
    void deleteById() {
        // given
        Customer customer1 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.NORMAL)
                .build();

        Customer customer2 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("hong")
                .customerEmail("hong@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        Customer customer3 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("song")
                .customerEmail("song@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();
        // when
        jdbcTemplateCustomerRepository.save(customer1);
        jdbcTemplateCustomerRepository.save(customer2);
        jdbcTemplateCustomerRepository.save(customer3);

        // then
        jdbcTemplateCustomerRepository.deleteById(customer1.getCustomerId());
    }

    @Test
    @DisplayName("저장소에서 모든 고객을 삭제한다")
    void deleteAll() {
        // given
        Customer customer1 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.NORMAL)
                .build();

        Customer customer2 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("hong")
                .customerEmail("hong@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        Customer customer3 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("song")
                .customerEmail("song@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        // when
        jdbcTemplateCustomerRepository.save(customer1);
        jdbcTemplateCustomerRepository.save(customer2);
        jdbcTemplateCustomerRepository.save(customer3);

        // then
        jdbcTemplateCustomerRepository.deleteAll();
    }

}
