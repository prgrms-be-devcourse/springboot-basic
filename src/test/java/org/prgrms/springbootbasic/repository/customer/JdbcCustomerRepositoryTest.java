package org.prgrms.springbootbasic.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.Customer;

class JdbcCustomerRepositoryTest {

    JdbcCustomerRepository jdbcCustomerRepository = new JdbcCustomerRepository();

    @DisplayName("모든 회원 조회 테스트")
    @Test
    void findAll() {
        //given
        //when
        var customers = jdbcCustomerRepository.findAll();

        //then
        assertThat(customers.size()).isEqualTo(4);
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
}