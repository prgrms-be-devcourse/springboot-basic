package org.prgrms.springbootbasic.repository.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcCustomerRepositoryTest {

    @DisplayName("모든 회원 조회 테스트")
    @Test
    void findAll() {
        //given
        JdbcCustomerRepository jdbcCustomerRepository = new JdbcCustomerRepository();

        //when
        var customers = jdbcCustomerRepository.findAll();

        //then
        Assertions.assertThat(customers.size()).isEqualTo(3);
    }
}