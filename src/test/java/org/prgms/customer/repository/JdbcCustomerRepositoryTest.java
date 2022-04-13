package org.prgms.customer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.customer.Customer;

import java.util.List;
import java.util.UUID;

class JdbcCustomerRepositoryTest {

    private final JdbcCustomerRepository jdbcCustomerRepository = new JdbcCustomerRepository();

    @BeforeEach
    void deleteAll() {
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("DB Customer 조회 기능 테스트")
    void findAllTest() {
        List<Customer> customers = jdbcCustomerRepository.findAll();
        System.out.println(customers);
    }

    @Test
    @DisplayName("이름으로 조회 테스트")
    void findByNameTest() {
        List<Customer> customers = jdbcCustomerRepository.findByName("tester00");
        System.out.println(customers);
        Assertions.assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("고객 데이터 insert 테스트")
    void inserTest() {
        UUID id = UUID.randomUUID();
        jdbcCustomerRepository.insertCustomer(new Customer(id, "geuno", "aaaa@gmail.com"));
        Assertions.assertThat(jdbcCustomerRepository.findByName("geuno").get(0).customerId()).isEqualTo(id);
        jdbcCustomerRepository.deleteById(id);
    }

    @Test
    @DisplayName("고객 정보 업데이트 테스트")
    public void updateTest() {
        jdbcCustomerRepository.insertCustomer(new Customer(UUID.randomUUID(), "geuno", "aaaa@gmail.com"));
        Customer geuno = jdbcCustomerRepository.findByName("geuno").get(0);
        Customer updateGeuno = new Customer(geuno.customerId(), "geunoUpdate", geuno.email());
        jdbcCustomerRepository.updateCustomer(updateGeuno);
        Assertions.assertThat(
                jdbcCustomerRepository.findByName("geunoUpdate")
                        .get(0).customerId()).isEqualTo(geuno.customerId());
    }
}