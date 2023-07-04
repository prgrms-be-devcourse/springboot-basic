package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.dto.CustomerUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class JdbcTemplateCustomerRepositoryTest {

    @Autowired
    private JdbcTemplateCustomerRepository jdbcTemplateCustomerRepository;

    @Test
    @DisplayName("고객을 생성하여 저장한다.")
    void save() {
        // given
        Customer customerExpect = new Customer(UUID.randomUUID(), "changhyeon", "chagnhyeon.h@kakao.com", CustomerType.NORMAL);

        // when
        Customer customerActual = jdbcTemplateCustomerRepository.save(customerExpect);

        // then
        assertThat(customerActual).isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("고객을 업데이트 한다.")
    void update() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "changhyeon", "chagnhyeon.h@kakao.com", CustomerType.NORMAL);
        CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto(customer.getCustomerId(), "hong", "hong@kakao.com", CustomerType.BLACKLIST);
        Customer customerExpect = new Customer(customer.getCustomerId(), customerUpdateDto.getCustomerName(), customerUpdateDto.getCustomerEmail(), customerUpdateDto.getCustomerType());

        // when
        jdbcTemplateCustomerRepository.save(customer);
        jdbcTemplateCustomerRepository.update(customerExpect);
        Optional<Customer> customerActual = jdbcTemplateCustomerRepository.findById(customerExpect.getCustomerId());

        // then
        assertThat(customerActual.get()).usingRecursiveComparison().isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("특정 고객을 조회한다.")
    void findById() {
        // given
        Customer customerExpect = new Customer(UUID.randomUUID(), "changhyeon", "changhyeon@kakao.com", CustomerType.NORMAL);

        // when
        jdbcTemplateCustomerRepository.save(customerExpect);

        Optional<Customer> customerActual = jdbcTemplateCustomerRepository.findById(customerExpect.getCustomerId());

        // then
        assertThat(customerActual.get()).usingRecursiveComparison().isEqualTo(customerExpect);
    }

    @Test
    @DisplayName("모든 고객을 조회한다.")
    void findAll() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "changhyeon", "chagnhyeon.h@kakao.com", CustomerType.NORMAL);
        Customer customer2 = new Customer(UUID.randomUUID(), "hong", "hong@kakao.com", CustomerType.BLACKLIST);

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
        Customer customer1 = new Customer(UUID.randomUUID(), "changhyeon", "chagnhyeon.h1@kakao.com", CustomerType.NORMAL);
        Customer customer2 = new Customer(UUID.randomUUID(), "hong", "hong@kakao.com", CustomerType.BLACKLIST);
        Customer customer3 = new Customer(UUID.randomUUID(), "song", "song@kakao.com", CustomerType.BLACKLIST);

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
        Customer customer1 = new Customer(UUID.randomUUID(), "changhyeon", "chagnhyeon.h1@kakao.com", CustomerType.NORMAL);
        Customer customer2 = new Customer(UUID.randomUUID(), "hong", "hong@kakao.com", CustomerType.BLACKLIST);
        Customer customer3 = new Customer(UUID.randomUUID(), "song", "song@kakao.com", CustomerType.BLACKLIST);

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
        Customer customer1 = new Customer(UUID.randomUUID(), "changhyeon", "chagnhyeon.h1@kakao.com", CustomerType.NORMAL);
        Customer customer2 = new Customer(UUID.randomUUID(), "hong", "hong@kakao.com", CustomerType.BLACKLIST);
        Customer customer3 = new Customer(UUID.randomUUID(), "song", "song@kakao.com", CustomerType.BLACKLIST);

        // when
        jdbcTemplateCustomerRepository.save(customer1);
        jdbcTemplateCustomerRepository.save(customer2);
        jdbcTemplateCustomerRepository.save(customer3);

        // then
        jdbcTemplateCustomerRepository.deleteAll();

    }
}
