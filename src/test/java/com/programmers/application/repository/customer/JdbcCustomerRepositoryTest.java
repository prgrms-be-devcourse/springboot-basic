package com.programmers.application.repository.customer;

import com.programmers.application.domain.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
//@Rollback(value = false)
class JdbcCustomerRepositoryTest {

    @Autowired
    private JdbcCustomerRepository jdbcCustomerRepository;

    @DisplayName("옳바른 이름과 이메일 입력 시, save()를 실행하면 테스트가 성공한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "aCustomer, mgtmh991013@naver.com",
            "bCustomer, mgtmh991013@gmail.com"
    })
    void save(String name, String email) {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, name, email);

        //when
        jdbcCustomerRepository.save(customer);

        //then
        Customer savedCustomer = jdbcCustomerRepository.findByCustomerId(customer.getCustomerId()).get();
        assertThat(customer).usingRecursiveComparison().isEqualTo(savedCustomer);
    }
}
