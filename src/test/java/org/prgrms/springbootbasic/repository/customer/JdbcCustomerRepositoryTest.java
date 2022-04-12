package org.prgrms.springbootbasic.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.Customer;

class JdbcCustomerRepositoryTest {

    JdbcCustomerRepository jdbcCustomerRepository = new JdbcCustomerRepository();

    @AfterEach
    void clean() {
        jdbcCustomerRepository.removeAll();
    }

    @DisplayName("모든 회원 조회 테스트")
    @Test
    void findAll() {
        //given
        jdbcCustomerRepository.save(new Customer(UUID.randomUUID(), "test00", "test00@gmail.com"));
        jdbcCustomerRepository.save(new Customer(UUID.randomUUID(), "test01", "test01@gmail.com"));
        jdbcCustomerRepository.save(new Customer(UUID.randomUUID(), "test02", "test02@gmail.com"));

        //when
        var customers = jdbcCustomerRepository.findAll();

        //then
        assertThat(customers.size()).isEqualTo(3);
    }

    @DisplayName("회원 저장 기능 테스트")
    @Test
    void save() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "hyuk", "hyuk@gmail.com");

        //when
        var saveCustomerId = jdbcCustomerRepository.save(customer);

        //then
        assertThat(saveCustomerId).isEqualTo(customer.getCustomerId());
    }

    @DisplayName("전체 회원 삭제 테스트")
    @Test
    void removeAll() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        jdbcCustomerRepository.save(customer);

        //when
        jdbcCustomerRepository.removeAll();

        //then
        assertThat(jdbcCustomerRepository.findAll().size()).isEqualTo(0);
    }

    @DisplayName("이름 변경 기능 테스트")
    @Test
    void changeName() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        jdbcCustomerRepository.save(customer);

        String newName = "newTest";

        //when
        jdbcCustomerRepository.changeName(customer.getCustomerId(), newName);

        //then
        var customers = jdbcCustomerRepository.findAll();
        assertThat(customers.get(0).getName()).isEqualTo(newName);
    }
}