package com.programmers.springweekly.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
    @DisplayName("고객을 생성하여 데이터베이스에 저장할 수 있다.")
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
    @DisplayName("데이터베이스에 저장된 고객을 새로운 정보를 받아 업데이트 할 수 있다.")
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
        Customer customerActual = jdbcTemplateCustomerRepository.findById(customerExpect.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("찾는 고객이 없습니다."));

        // then
        assertThat(customerActual).usingRecursiveComparison().isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("고객을 저장하고 저장한 고객의 ID로 데이터베이스에서 조회하여 고객을 가져올 수 있다.")
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

        Customer customerActual = jdbcTemplateCustomerRepository.findById(customerExpect.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("찾는 고객이 없습니다."));

        // then
        assertThat(customerActual).usingRecursiveComparison().isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("여러 고객을 저장하고 데이터베이스에서 저장된 모든 고객을 조회할 수 있다.")
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
    @DisplayName("여러 타입의 고객을 저장하고 저장된 고객중 타입이 블랙리스트인 고객을 데이터베이스에서 가져올 수 있다.")
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
    @DisplayName("고객을 저장하고 저장한 고객의 ID를 데이터베이스에서 찾아 삭제할 수 있다.")
    void deleteById() {
        // given
        Customer customer1 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon")
                .customerEmail("changhyeon.h@kakao.com")
                .customerType(CustomerType.NORMAL)
                .build();

        jdbcTemplateCustomerRepository.save(customer1);

        // when
        jdbcTemplateCustomerRepository.deleteById(customer1.getCustomerId());
        Optional<Customer> cutomerActual = jdbcTemplateCustomerRepository.findById(customer1.getCustomerId());

        // then
        assertThat(cutomerActual).isEmpty();
    }

    @Test
    @DisplayName("여러 고객들을 저장하고 데이터베이스에서 모든 고객을 삭제할 수 있다.")
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

        jdbcTemplateCustomerRepository.save(customer1);
        jdbcTemplateCustomerRepository.save(customer2);
        jdbcTemplateCustomerRepository.save(customer3);

        // when
        jdbcTemplateCustomerRepository.deleteAll();

        // then
        assertThat(jdbcTemplateCustomerRepository.findAll().size()).isEqualTo(0);

    }

}
