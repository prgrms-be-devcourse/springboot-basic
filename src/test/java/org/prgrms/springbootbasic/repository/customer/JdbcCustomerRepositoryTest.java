package org.prgrms.springbootbasic.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.zaxxer.hikari.HikariDataSource;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prgrms.springbootbasic.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @BeforeAll
    void init() {
        jdbcCustomerRepository.removeAll();
    }

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

    @DisplayName("이메일로 회원 조회 테스트")
    @ParameterizedTest
    @CsvSource(value = {"test@gmail.com, 1", "test2@gmail.com, 0"})
    void findByEmail(String email, int expected) {
        //given
        jdbcCustomerRepository.save(new Customer(UUID.randomUUID(), "test", "test@gmail.com"));

        //when
        int actual = jdbcCustomerRepository.findByEmail(email).size();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Configuration
    @ComponentScan(
        basePackages = {"org.prgrms.springbootbasic.repository.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/springboot_basic")
                .username("hyuk")
                .password("hyuk1234!")
                .type(HikariDataSource.class)
                .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
}
